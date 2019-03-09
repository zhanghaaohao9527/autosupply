package com.kc.auto.supply.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HttpClientTool {
    private final static int DEFAULT_MAX_CONNECTION = 30;
    private final static int DEFAULT_DEFAULT_MAX_CONNECTION = 30;

    private final static int DEFAULT_SOCKET_TIME_OUT = 60000;
    private final static int DEFAULT_CONNECT_TIME_OUT = 60000;
    private final static int DEFAULT_CONNECT_REQUEST_TIME_OUT = 60000;
    private final static int DEFAULT_MAX_PER_ROUTE = 20;

    /**
     * default keepAliveTimeout, this timeout must match server configuration
     */
    private static final int DEFAULT_KEEP_ALIVE_TIME_OUT = 60 * 1000;

    private PoolingHttpClientConnectionManager connectionManager = null;
    private HttpClientBuilder httpBuilder = null;
    private CloseableHttpClient httpClient = null;
    private RequestConfig requestConfig = null;

    private final String ip;
    private final int port;
    private final String scheme;
    private Integer socketTimeout;
    private Integer connectTimeout;
    private Integer connectionRequestTimeout;
    private Integer maxTotal;
    private Integer defaultMaxPerRoute;
    private Integer maxPerRoute;

    protected HttpClientTool(String ip, int port, String scheme) {
        this.ip = ip;
        this.port = port;
        this.scheme = scheme;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getScheme() {
        return scheme;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public Integer getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(Integer maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }

    /**
     * 初始化连接管理
     */
    protected void init() {
        socketTimeout = (null == socketTimeout) ? DEFAULT_SOCKET_TIME_OUT : socketTimeout;
        connectTimeout = (null == connectTimeout) ? DEFAULT_CONNECT_TIME_OUT : connectTimeout;
        connectionRequestTimeout = (null == connectionRequestTimeout) ?
            DEFAULT_CONNECT_REQUEST_TIME_OUT : connectionRequestTimeout;
        maxTotal = (null == maxTotal) ? DEFAULT_MAX_CONNECTION : maxTotal;
        defaultMaxPerRoute = (null == defaultMaxPerRoute) ?
            DEFAULT_DEFAULT_MAX_CONNECTION : defaultMaxPerRoute;
        maxPerRoute = (null == maxPerRoute) ? DEFAULT_MAX_PER_ROUTE : maxPerRoute;

        // 设置http的状态参数
        requestConfig = RequestConfig.custom()
            .setSocketTimeout(socketTimeout)
            .setConnectTimeout(connectTimeout)
            .setConnectionRequestTimeout(connectionRequestTimeout)
            .build();

        HttpHost target = new HttpHost(ip, port);

        connectionManager = new PoolingHttpClientConnectionManager();
        // 客户端并行连接最大数
        connectionManager.setMaxTotal(maxTotal);
        // 每个主机的最大并行连接数
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setMaxPerRoute(new HttpRoute(target), maxPerRoute);

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                // 如果已经重试了2次，就放弃
                if (executionCount >= 2) {
                    return false;
                }
                // 如果服务器丢掉了连接，那么就重试
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                // 不要重试SSL握手异常
                if (exception instanceof SSLHandshakeException) {
                    return false;
                }
                // 超时
                if (exception instanceof InterruptedIOException) {
                    return false;
                }
                // 目标服务器不可达
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                // 连接被拒绝
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }
                // SSL握手异常
                if (exception instanceof SSLException) {
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();

                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }

        };

        httpBuilder = HttpClients.custom()
            .setConnectionManager(connectionManager)
            .setRetryHandler(httpRequestRetryHandler)
            .setKeepAliveStrategy((HttpResponse response, HttpContext context) -> {
                HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && "timeout".equalsIgnoreCase(param)) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }
                return DEFAULT_KEEP_ALIVE_TIME_OUT;
            });

        httpClient = httpBuilder.build();
    }

    /**
     * 获取httpclient
     * @return CloseableHttpClient
     */
    private CloseableHttpClient getConnection() {
        if (null == httpBuilder) {
            throw new RuntimeException("please call init first.");
        }

        if (null == httpClient) {
            throw new RuntimeException("please call init first.");
        }
        return httpClient;
    }

    @Deprecated
    public HttpUriRequest getRequestMethod(Map<String, String> map, String url, String method) {
        List<NameValuePair> params = new ArrayList<>();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> e : entrySet) {
            String name = e.getKey();
            String value = e.getValue();
            NameValuePair pair = new BasicNameValuePair(name, value);
            params.add(pair);
        }
        HttpUriRequest reqMethod = null;
        if ("post".equals(method)) {
            reqMethod = RequestBuilder.post().setUri(url)
                .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                .setConfig(requestConfig).build();
        } else if ("get".equals(method)) {
            reqMethod = RequestBuilder.get().setUri(url)
                .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                .setConfig(requestConfig).build();
        }
        return reqMethod;
    }

    public <T> T doPost(String url, String json, Class<T> t) throws Exception {
        URI uri = new URI(scheme, null, ip, port, url, null, null);
        HttpClient httpClient = getConnection();
        HttpPost post = new HttpPost(uri);
        T response;
        try {
            StringEntity s = new StringEntity(json);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            post.setConfig(requestConfig);

            HttpResponse res = httpClient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(entity);
                response = JSONObject.parseObject(result, t);
            }
            else {
                throw new Exception(res.toString());
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            post.releaseConnection();
            connectionManager.closeExpiredConnections();
            connectionManager.closeIdleConnections(120, TimeUnit.SECONDS);
        }
        return response;
    }

    public <T> T doGet(String url, Class<T> t) throws Exception {
        URI uri = new URI(scheme, null, ip, port, url, null, null);
        HttpClient httpClient = getConnection();
        HttpGet get = new HttpGet(uri);
        T response;
        try {
            HttpResponse res = httpClient.execute(get);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(entity);
                response = JSONObject.parseObject(result, t);
            }
            else {
                throw new Exception(res.toString());
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            get.releaseConnection();
            connectionManager.closeExpiredConnections();
            connectionManager.closeIdleConnections(120, TimeUnit.SECONDS);
        }
        return response;
    }
}
