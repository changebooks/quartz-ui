Ajax = {
    /**
     * Method Get
     */
    METHOD_GET: "GET",

    /**
     * Method Post
     */
    METHOD_POST: "POST",

    /**
     * Method Delete
     */
    METHOD_DELETE: "DELETE",

    /**
     * Content Type
     */
    CONTENT_TYPE_NAME: "Content-Type",

    /**
     * Content Type application/json
     */
    CONTENT_TYPE_JSON: "application/json;charset=UTF-8",

    /**
     * GET请求
     *
     * p = {
     *     url:       "",
     *     parameter: "",
     *     success:   function(data) {},
     *     error:     function(code, message) {}
     * }
     */
    get: function (p) {
        if (typeof (p) === 'object') {
            p.method = Ajax.METHOD_GET;
        }

        Ajax.req(p);
    },

    /**
     * POST请求
     *
     * p = {
     *     url:       "",
     *     parameter: "",
     *     success:   function(data) {},
     *     error:     function(code, message) {}
     * }
     */
    post: function (p) {
        if (typeof (p) === 'object') {
            p.method = Ajax.METHOD_POST;
        }

        Ajax.req(p);
    },

    /**
     * DELETE请求
     *
     * p = {
     *     url:       "",
     *     parameter: "",
     *     success:   function(data) {},
     *     error:     function(code, message) {}
     * }
     */
    delete: function (p) {
        if (typeof (p) === 'object') {
            p.method = Ajax.METHOD_DELETE;
        }

        Ajax.req(p);
    },

    /**
     * 请求
     *
     * p = {
     *     method:    "GET|POST",
     *     url:       "",
     *     parameter: "",
     *     success:   function(data) {},
     *     error:     function(code, message) {}
     * }
     */
    req: function (p) {
        if (typeof (p) !== 'object') {
            console.error("Ajax.req()'parameter must be a json object");
            return;
        }

        if (typeof (p.success) !== 'function') {
            console.error("Ajax.req()'parameter.success must be a function");
            return;
        }

        if (typeof (p.error) !== 'function') {
            console.error("Ajax.req()'parameter.error must be a function");
            return;
        }

        Ajax.doReq(p.method, p.url, p.parameter, function (response) {
            if (response.code === 0) {
                p.success(response.data);
            } else {
                console.error("req failed, response: " + JSON.stringify(response));
                p.error(response.code, response.message);
            }
        });
    },

    /**
     * 执行请求
     *
     * @param method    GET | POST
     * @param url       /domain:port/do
     * @param parameter GET: a=1&b=2; POST: {}
     * @param complete  function(response) {}
     */
    doReq: function (method, url, parameter, complete) {
        if (method === undefined || method === "") {
            console.error("method can't be undefined or empty");
            return;
        }

        if (url === undefined || url === "") {
            console.error("url can't be undefined or empty");
            return;
        }

        if (typeof (complete) !== 'function') {
            console.error("complete must be a function");
            return;
        }

        method = method.toUpperCase();
        if (method === Ajax.METHOD_GET || method === Ajax.METHOD_DELETE) {
            url = Ajax.joinParameter(url, parameter);
            parameter = "";
        }

        let xhrObj = new XMLHttpRequest();
        xhrObj.open(method, url, true);

        if (method === Ajax.METHOD_POST) {
            xhrObj.setRequestHeader(Ajax.CONTENT_TYPE_NAME, Ajax.CONTENT_TYPE_JSON);
            if (typeof (parameter) === 'object') {
                parameter = JSON.stringify(parameter);
            }
        }

        xhrObj.send(parameter);
        xhrObj.onreadystatechange = function () {
            /**
             * readyState 值
             * 2 - 已发送数据，未收到反馈
             * 3 - 发送数据中，已收到反馈
             * 4 - 接收数据完毕
             */
            if (xhrObj.readyState === 4) {
                let status = xhrObj.status;
                let responseText = xhrObj.responseText;

                if (status === 200) {
                    if (Ajax.isJson(responseText)) {
                        eval("responseText = " + responseText + ";");
                    }

                    complete(responseText);
                } else {
                    console.error("doReq failed, status: " + status + ", responseText: " + responseText);
                }
            }
        }
    },

    /**
     * Json字符串？
     */
    isJson: function (E) {
        return E !== undefined && (E.indexOf("{") > -1) && (E.indexOf("}") > -1);
    },

    /**
     * 拼接参数
     */
    joinParameter: function (url, parameter) {
        if (url === undefined || url === "") {
            return "?" + parameter;
        }

        if (parameter === undefined || parameter === "") {
            return url;
        }

        if (url.indexOf("?") === -1) {
            return url + "?" + parameter;
        }

        if (Ajax.endWith(url, "&") ||
            Ajax.startWith(parameter, "&")) {
            return url + parameter;
        }

        return url + "&" + parameter;
    },

    /**
     * 匹配字符串前缀
     */
    startWith: function (E, v) {
        return E.substr(0, v.length) === v;
    },

    /**
     * 匹配字符串后缀
     */
    endWith: function (E, v) {
        let p = E.length - v.length;
        return E.substr(p) === v;
    }

};
