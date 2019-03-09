package com.kc.auto.supply.http;

/**
 * HttpClientTool构造器
 * @author wuxiaowu
 */
public class HttpClientToolBuilder {
    private HttpClientTool httpClientTool;

    public HttpClientToolBuilder(String ip, int port, String scheme) {
        this.httpClientTool = new HttpClientTool(ip, port, scheme);
    }

    public HttpClientToolBuilder setSocketTimeout(Integer socketTimeout) {
        this.httpClientTool.setSocketTimeout(socketTimeout);
        return this;
    }

    public HttpClientToolBuilder setConnectTimeout(Integer connectTimeout) {
        this.httpClientTool.setConnectTimeout(connectTimeout);
        return this;
    }

    public HttpClientToolBuilder setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.httpClientTool.setConnectionRequestTimeout(connectionRequestTimeout);
        return this;
    }

    public HttpClientToolBuilder setMaxTotal(Integer maxTotal) {
        this.httpClientTool.setMaxTotal(maxTotal);
        return this;
    }

    public HttpClientToolBuilder setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
        this.httpClientTool.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return this;
    }

    public HttpClientToolBuilder setMaxPerRoute(Integer maxPerRoute) {
        this.httpClientTool.setMaxPerRoute(maxPerRoute);
        return this;
    }

    public HttpClientTool build() {
        this.httpClientTool.init();
        return this.httpClientTool;
    }
}
