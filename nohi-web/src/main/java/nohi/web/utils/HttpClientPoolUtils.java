package nohi.web.utils;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.util.TimeValue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

/**
 * http连接池工具
 *
 * @author NOHI
 * @date 2022/8/25 22:04
 **/
@Slf4j
public class HttpClientPoolUtils {
    /**
     * 默认字符集
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/json";
    /**
     * 默认字符集
     */
    private static final Charset DEFAULT_CHARSET = HttpClientConfig.standardCharsets;

    /**
     * 最大连接数
     */
    private final static int HTTP_POOL_MAX_TOTAL = HttpClientConfig.httpPoolMaxPoolSize;
    /**
     * 单路由最大连接数
     */
    private final static int HTTP_POOL_MAX_PER_ROUTE = HttpClientConfig.httpPoolMaxPerRoute;
    /**
     * 线程池监控时间间隔
     */
    private final static int HTTP_POOL_MONITOR_INTERVAL = HttpClientConfig.httpPoolMonitorInterval;

    /**
     * 线程池监控时间间隔
     */
    private final static int HTTP_POOL_IDEL_TIMEOUT = HttpClientConfig.httpPoolIdelTimeout;
    private final static int CONNECT_TIMEOUT = HttpClientConfig.httpConnectTimeout;
    private final static int CONNECTION_REQUEST_TIMEOUT = HttpClientConfig.httpRequestTimeout;
    private final static int RESPONSE_TIMEOUT = HttpClientConfig.httpResponseTimeout;
    /**
     * HTTP 状态码 200 正常
     */
    private final static int HTTP_STATUS_200 = 200;

    /**
     * 发送请求的客户端单例
     */
    private final static CloseableHttpClient DEFAULT_HTTP_CLIENT;
    /**
     * http连接池对象
     */
    private final static PoolingHttpClientConnectionManager CM;
    /**
     * 默认 http Client 连接属性
     */
    private final static RequestConfig DEFAULT_REQUEST_CONFIG;


    /*
     * 初始化连接池
     */
    static {
        // 创建httpClient连接池 指定连接超时时间 单位毫秒
        CM = new PoolingHttpClientConnectionManager();
        // 最大连接数
        CM.setMaxTotal(HTTP_POOL_MAX_TOTAL);
        // 单路由最大连接数
        // 未通过调用 setMaxPerRoute 指定的路由所允许的最大连接数。
        // 当您提前知道路由时使用 setMaxPerRoute，
        // 当您不知道时使用 setDefaultMaxPerRoute。
        CM.setDefaultMaxPerRoute(HTTP_POOL_MAX_PER_ROUTE);

        /*
         * // 按路由设置
         * // HttpHost httpHost = new HttpHost(host, port);
         * // manager.setMaxPerRoute(new HttpRoute(httpHost), MAX_ROUTE);
         *
         *设置请求参数配置，创建连接时间、从连接池获取连接时间、数据传输时间、是否测试连接可用、构建配置对象
         */
        DEFAULT_REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS).setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS).setResponseTimeout(RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS).build();
        // 创建默认 http client连接
        DEFAULT_HTTP_CLIENT = getHttpClient();
    }

    /**
     * 从默认连接池中获取httpClient连接
     */
    public static CloseableHttpClient getHttpClient() {
        return getHttpClient(CM);
    }

    /**
     * 从连接池中获取httpclient连接，指定超时时间
     *
     * @param requestConfig 连接配置
     * @return 可关闭httpClient连接
     */
    public static CloseableHttpClient getHttpClient(RequestConfig requestConfig) {
        return getHttpClient(CM, requestConfig);
    }

    /**
     * 从连接池中获取httpclient连接，指定超时时间
     *
     * @param connectionRequestTimeout 获取连接超时时间
     * @param connectTimeout           连接超时时间
     * @param socketTimeout            请求响应超时时间
     * @return 可关闭httpClient连接
     */
    public static CloseableHttpClient getHttpClient(int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        // 设置请求参数配置，创建连接时间、从连接池获取连接时间、数据传输时间、是否测试连接可用、构建配置对象
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout, TimeUnit.MILLISECONDS).setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS).setResponseTimeout(socketTimeout, TimeUnit.MILLISECONDS).build();
        return getHttpClient(CM, requestConfig);
    }

    /**
     * 从连接池中获取httpClient连接
     */
    public static CloseableHttpClient getHttpClient(PoolingHttpClientConnectionManager cm) {
        return getHttpClient(cm, DEFAULT_REQUEST_CONFIG);
    }

    /**
     * 从连接池中获取httpClient连接
     */
    public static CloseableHttpClient getHttpClient(PoolingHttpClientConnectionManager cm, RequestConfig requestConfig) {
        // 创建httpClient时从连接池中获取，并设置连接失败时自动重试（也可以自定义重试策略：setRetryHandler()）
        // 可以设置请求重试方法，这里忽略
        // 开启监控线程,对异常和空闲线程进行关闭
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(cm).disableAutomaticRetries().build();
        /*
         * 线程池监控
         */
        ScheduledExecutorService monitorExecutor = newScheduledThreadPool(1);
        final int[] i = {0};
        monitorExecutor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                i[0]++;
                //关闭异常连接
                CM.closeExpired();
                //关闭5s空闲的连接
                CM.closeIdle(TimeValue.of(HTTP_POOL_IDEL_TIMEOUT, TimeUnit.MILLISECONDS));
                if (i[0] % 100 == 0) {
                    i[0] = 0;
                    log.info("close expired and idle for over 5s connection,print per 5 * 100 s");
                }
            }
        }, HTTP_POOL_MONITOR_INTERVAL, HTTP_POOL_MONITOR_INTERVAL, TimeUnit.MILLISECONDS);
        return client;
    }

    /**
     * 对http请求进行基本设置
     *
     * @param httpRequestBase http请求
     */
    private static void setRequestConfig(HttpUriRequestBase httpRequestBase) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS).setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS).setResponseTimeout(RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS).build();
        httpRequestBase.setConfig(requestConfig);
    }


    /**
     * 设置请求头
     *
     * @param method
     * @param properties
     */
    private static void setHead(HttpUriRequestBase method, Properties properties) {
        if (properties == null || properties.isEmpty()) {
            return;
        }
        for (Iterator iterator = properties.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            method.setHeader(key, value);
        }
    }

    /**
     * 设置请求体
     *
     * @param httpPost
     * @param params
     */
    private static void setPostParam(HttpPost httpPost, Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            log.debug("setPostParam 请求参数列表为空");
            return;
        }
        List<NameValuePair> nvps = new ArrayList<>();
        for (String key : params.keySet()) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET));
    }

    private static void setPostMsg(HttpPost httpPost, String postMsg, String contentType, String charSet) {
        if (StringUtils.isNotBlank(postMsg)) {
            postMsg = postMsg.trim();
        }
        ContentType ct = ContentType.create(contentType, null == charSet ? DEFAULT_CHARSET : Charset.forName(charSet));
        httpPost.setEntity(new StringEntity(postMsg, ct, false));
    }

    /**
     * 发送POST请求
     *
     * @param url     请求地址
     * @param postMsg 请求消息
     * @return
     */
    public static String post(String url, String postMsg) {
        return post(url, postMsg, null, DEFAULT_CONTENT_TYPE, DEFAULT_CHARSET.name());
    }

    /**
     * 发送POST请求，指定contentType
     *
     * @param url         请求地址
     * @param postMsg     请求消息
     * @param contentType contentType
     * @return 返回报文
     */
    public static String post(String url, String postMsg, String contentType) {
        return post(url, postMsg, null, contentType, DEFAULT_CHARSET.name());
    }

    /**
     * 发送请求 指定http头 消息 contentType
     *
     * @param url         请求地址
     * @param properties  指定http头
     * @param postMsg     消息
     * @param contentType contentType
     * @return 报文
     */
    public static String post(String url, Properties properties, String postMsg, String contentType) {
        return post(url, postMsg, properties, contentType, DEFAULT_CHARSET.name());
    }

    /**
     * 默认http client 发送请求
     *
     * @param url         请求地址
     * @param properties  指定http头
     * @param postMsg     消息
     * @param contentType contentType
     * @param charSet     字符集
     * @return 报文
     */
    public static String post(String url, String postMsg, Properties properties, String contentType, String charSet) {
        return post(DEFAULT_HTTP_CLIENT, url, postMsg, properties, contentType, charSet);
    }

    /**
     * 默认http client 发送请求
     *
     * @param httpClient  httpClient
     * @param url         请求地址
     * @param properties  指定http头
     * @param postMsg     消息
     * @param contentType contentType
     * @param charSet     字符集
     * @return 报文
     */
    public static String post(CloseableHttpClient httpClient, String url, String postMsg, Properties properties, String contentType, String charSet) {
        HttpPost http = new HttpPost(url);
        // setRequestConfig(httpPost);
        CloseableHttpResponse httpResponse = null;
        InputStream in = null;

        // 设置http head
        setHead(http, properties);
        // 设置请求报文体
        setPostMsg(http, postMsg, contentType, charSet);

        try {
            httpResponse = httpClient.execute(http);
            HttpEntity entity = httpResponse.getEntity();
            log.debug("[{}]响应[{}]", url, httpResponse.getCode());
            if (entity != null) {
                return EntityUtils.toString(entity, charSet);
            }
        } catch (IOException | ParseException e) {
            log.error("请求[{}]交易异常:{}", url, e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (httpResponse != null) {
                //执行httpResponse.close关闭对象会关闭连接池，
                //如果需要将连接释放到连接池，可以使用EntityUtils.consume()方法
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                } catch (IOException e) {
                    log.error("释放[{}]httpResponse异常:{}", url, e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * 执行请求
     */
    public static String doGetRequest(String url) {
        return doGetRequest(DEFAULT_HTTP_CLIENT, url);
    }

    /**
     * 执行GET请求，返回报文
     * 翻译连接至连接池
     */
    public static String doGetRequest(CloseableHttpClient httpClient, String url) {
        // 创建http请求类型
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            log.debug("[{}]响应[{}]Phrase[{}]", url, httpResponse.getCode(), httpResponse.getReasonPhrase());
            if (HTTP_STATUS_200 == httpResponse.getCode()) {
                return EntityUtils.toString(httpResponse.getEntity());
            } else {
                return EntityUtils.toString(httpResponse.getEntity());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            if (httpResponse != null) {
                // 执行httpResponse.close关闭对象会关闭连接池，
                // 如果需要将连接释放到连接池，可以使用EntityUtils.consume()方法
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                } catch (IOException e) {
                    log.error("[{}]释放连接异常:{}", url, e.getMessage());
                }
            }
        }
    }
}
