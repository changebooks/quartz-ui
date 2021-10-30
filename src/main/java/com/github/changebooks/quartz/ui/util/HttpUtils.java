package com.github.changebooks.quartz.ui.util;

import com.google.common.base.Preconditions;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Http请求
 *
 * @author changebooks
 */
public final class HttpUtils {
    /**
     * 请求结果
     */
    public static final class Result implements Serializable {
        /**
         * 状态码
         */
        private int statusCode;

        /**
         * 内容
         */
        private String data;

        public boolean isOk() {
            return statusCode == HttpStatus.SC_OK;
        }

        @Override
        public String toString() {
            return JsonParser.toJson(this);
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

    }

    /**
     * Http客户端
     */
    private static final CloseableHttpClient HTTP_CLIENT = HttpClientBuilder.create().build();

    private HttpUtils() {
    }

    /**
     * Send Post Request
     *
     * @param url 请求接口
     * @return Result
     */
    public static Result reqPost(String url) throws IOException {
        Preconditions.checkArgument(!StringUtils.isEmpty(url), "url can't be empty");

        HttpPost httpPost = new HttpPost(url);
        return execute(HTTP_CLIENT, httpPost);
    }

    /**
     * Send Http Request
     *
     * @param httpClient  HttpClientBuilder.create().build();
     * @param httpRequest HttpGet、HttpPost、HttpPut、HttpDelete
     * @return Result
     */
    public static Result execute(final CloseableHttpClient httpClient, HttpRequestBase httpRequest) throws IOException {
        Preconditions.checkNotNull(httpClient, "httpClient can't be null");
        Preconditions.checkNotNull(httpRequest, "httpRequest can't be null");

        CloseableHttpResponse httpResponse = httpClient.execute(httpRequest);
        Preconditions.checkState(Objects.nonNull(httpResponse), "httpResponse can't be null");

        try {
            return readResponse(httpResponse, Constants.CHARSET);
        } finally {
            httpResponse.close();
        }
    }

    /**
     * Read Response
     *
     * @param httpResponse CloseableHttpClient.execute(HttpRequestBase)
     * @param charset      字符编码
     * @return Result
     */
    public static Result readResponse(final CloseableHttpResponse httpResponse, final Charset charset) throws IOException {
        Preconditions.checkNotNull(httpResponse, "httpResponse can't be null");
        Preconditions.checkNotNull(charset, "charset can't be null");

        StatusLine statusLine = httpResponse.getStatusLine();
        Preconditions.checkState(Objects.nonNull(statusLine), "statusLine can't be null");

        Result result = new Result();
        result.setStatusCode(statusLine.getStatusCode());

        HttpEntity httpEntity = httpResponse.getEntity();
        if (Objects.isNull(httpEntity)) {
            return result;
        }

        try {
            String data = EntityUtils.toString(httpEntity, charset);
            result.setData(data);
            return result;
        } finally {
            EntityUtils.consume(httpEntity);
        }
    }

}
