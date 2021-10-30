package com.github.changebooks.quartz.ui.util;

import java.io.Serializable;

/**
 * Data Transfer Object
 *
 * @author changebooks
 */
public final class Result<T> implements Serializable {
    /**
     * 错误码
     * 0：正确、> 0：错误、< 0：弃用
     */
    private int code;

    /**
     * 错误信息
     * ok：正确、!ok：错误
     */
    private String message;

    /**
     * 内容
     */
    private T data;

    public Result() {
        this.code = Constants.SYSTEM_RUN_ERR;
        this.message = Constants.MSG_SYSTEM_RUN_ERR;
    }

    /**
     * OK
     */
    public static <T> Result<T> toSuccess(T data) {
        Result<T> result = new Result<>();

        result.setCode(Constants.SUCCESS_NUM);
        result.setMessage(Constants.MSG_SUCCESS);
        result.setData(data);

        return result;
    }

    /**
     * 错误码和错误信息
     */
    public static <T> Result<T> fromMessage(int code, String message) {
        Result<T> result = new Result<>();

        result.setCode(code);
        result.setMessage(message);

        return result;
    }

    /**
     * 异常
     */
    public static <T> Result<T> fromThrowable(Throwable tr) {
        Result<T> result = new Result<>();

        if (tr != null) {
            result.setMessage(tr.getMessage());
        }

        return result;
    }

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
