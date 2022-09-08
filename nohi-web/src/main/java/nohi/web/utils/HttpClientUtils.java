package nohi.web.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author NOHI
 * @program: springboot-webservice
 * @description:
 * @create 2020-05-17 11:23
 **/

public class HttpClientUtils {
    private static final Logger log = LoggerFactory.getLogger( HttpClientUtils.class);

    private HttpClient client = null;

    public HttpClientUtils() {
        client = createHttpClient();
    }

    /**
     * 根据参数创建httpclient
     *
     * @param managerParams
     */
    public HttpClientUtils(HttpConnectionManagerParams managerParams) {
        client = createHttpClient(managerParams);
    }

    /**
     * 创建httpclient
     *
     * @return
     */
    private HttpClient createHttpClient() {
        HttpConnectionManagerParams managerParams = new HttpConnectionManagerParams();
        managerParams.setConnectionTimeout(5000); // 设置连接超时时间
        managerParams.setDefaultMaxConnectionsPerHost(100);
        managerParams.setSoTimeout(120000); // 设置读取数据超时时间

        return createHttpClient(managerParams);
    }

    /**
     * 创建httpclient
     *
     * @return
     */
    private HttpClient createHttpClient(HttpConnectionManagerParams managerParams) {
        HttpClientParams clientParams = new HttpClientParams();
        HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        httpConnectionManager.setParams(managerParams);
        HttpClient client = new HttpClient(clientParams);
        client.setHttpConnectionManager(httpConnectionManager);
        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0,false));
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
        return client;
    }

    public GetMethod get(String url) throws Exception {
        return this.get(url, null);
    }

    public GetMethod get(String url, Properties properties) throws Exception {
        HttpMethod method = new GetMethod(url);
        return (GetMethod) this.send(method, properties);
    }

    public PostMethod post(String url, String postMsg) throws Exception {
        return this.post(url, null, postMsg , null);
    }
    public PostMethod postJson(String url, String postMsg) throws Exception {
        return this.post(url, null, postMsg,"application/json");
    }
    public PostMethod postJson(String url, Properties properties , String postMsg) throws Exception {
        return this.post(url, properties, postMsg,"application/json");
    }
    public PostMethod post(String url, Properties properties, String postMsg , String contentType) throws Exception {
        PostMethod method = new PostMethod(url);
        if (StringUtils.isNotBlank(postMsg)) {
            try {
                ((EntityEnclosingMethod) method).setRequestEntity(new StringRequestEntity(postMsg, contentType, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
                //throw new HttpException(e.getMessage());
            }
        }
        return (PostMethod) this.send(method, properties);
    }


    public PostMethod post(String url, Properties properties, List<NameValuePair> nvps) throws Exception {
        PostMethod method = new PostMethod(url);
        if (null != nvps && !nvps.isEmpty()) {
            NameValuePair[] array = new NameValuePair[nvps.size()];
            array = nvps.toArray(array);
            method.setRequestBody((NameValuePair[]) array);
        }
        return (PostMethod) this.send(method, properties);
    }

    /**
     * 执行方法
     *
     * @param method
     * @param properties
     * @return
     */
    public HttpMethod send(HttpMethod method, Properties properties) throws Exception {

        // 设置请求头
        this.setHead(method, properties);

        try {
            int c = client.executeMethod(method);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            // method.releaseConnection();
        }

        return method;
    }

    /**
     * 设置请求头
     *
     * @param method
     * @param properties
     */
    private void setHead(HttpMethod method, Properties properties) {
        if (properties != null && properties.size() > 0) {
            for (Iterator iterator = properties.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                method.setRequestHeader(key, value);
            }
        }
    }

}

