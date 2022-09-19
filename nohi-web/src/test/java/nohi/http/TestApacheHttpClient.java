package nohi.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>测试apache http client</p>
 * @date 2022/09/14 17:13
 **/
@Slf4j
public class TestApacheHttpClient {

    /**
     * 同步Get请求
     */
    @Test
    public void testGet() {
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建 HttpGet 请求
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8099/mock/1.json");
        // 设置长连接
        httpGet.setHeader("Connection", "keep-alive");
        // 设置代理（模拟浏览器版本）
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        // 设置 Cookie
        httpGet.setHeader("Cookie", "UM_distinctid=16442706a09352-0376059833914f-3c604504-1fa400-16442706a0b345; CNZZDATA1262458286=1603637673-1530123020-%7C1530123020; JSESSIONID=805587506F1594AE02DC45845A7216A4");

        CloseableHttpResponse httpResponse = null;
        try {
            // 请求并获得响应结果
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            // 输出请求结果
            log.info(EntityUtils.toString(httpEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 无论如何必须关闭连接
        finally {
            IOUtils.closeQuietly(httpResponse);
            IOUtils.closeQuietly(httpClient);
        }
    }

    /**
     * 同步POST请求
     */
    @Test
    public void post() {
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8888/mock/http?sleep=800");
        // 设置长连接
        httpPost.setHeader("Connection", "keep-alive");
        // 设置代理（模拟浏览器版本）
//        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        // 设置 Cookie
//        httpPost.setHeader("Cookie", "UM_distinctid=16442706a09352-0376059833914f-3c604504-1fa400-16442706a0b345; CNZZDATA1262458286=1603637673-1530123020-%7C1530123020; JSESSIONID=805587506F1594AE02DC45845A7216A4");

        // 创建 HttpPost 参数
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("draw", "1"));
        params.add(new BasicNameValuePair("start", "0"));
        params.add(new BasicNameValuePair("length", "10"));

        CloseableHttpResponse httpResponse = null;
        try {
            // 设置 HttpPost 参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, Charset.forName("UTF-8")));
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            // 输出请求结果
            System.out.println(EntityUtils.toString(httpEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 无论如何必须关闭连接
        finally {
            IOUtils.closeQuietly(httpResponse);
            IOUtils.closeQuietly(httpClient);
        }
    }


    /**
     * 异步HTTP Client
     * httpclient5 提供
     */
    @Test
    public void testGetSync() {
        String url = "http://127.0.0.1:8099/mock/1.json";
        try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
            client.start();
            final SimpleHttpRequest request = SimpleRequestBuilder.get().setUri(url).build();
            Future<SimpleHttpResponse> future = client.execute(request, new FutureCallback<SimpleHttpResponse>() {
                @Override
                public void completed(SimpleHttpResponse simpleHttpResponse) {
                    log.info("...SimpleHttpRequest.completed");
                    String msg = simpleHttpResponse.getBodyText();
                    log.debug("msg:{}", msg);
                }

                @Override
                public void failed(Exception e) {
                    log.error("...SimpleHttpRequest.failed:{}", e.getMessage());
                }

                @Override
                public void cancelled() {
                    log.info("...SimpleHttpRequest.cancelled");
                }
            });

            SimpleHttpResponse response = future.get();

            log.info("response.status[{}]", response.getCode());
            log.info("response.reasonPhrase[{}]", response.getReasonPhrase());

        } catch (Exception e) {
            log.error("{} 异常:{}", e.getMessage());
        }
    }

    /**
     * 异步HTTP Client
     * httpclient5 提供
     */
    @Test
    public void testPostSync() {
        String url = "http://127.0.0.1:8888/mock/http?sleep=1000";
        String reqMsg = "{\n" + "\"retCode\": \"\",\n" + "\"retMsg\": \"\",\n" + "\"data\": {\n" + "\"a\": \"这是请求\",\n" + "\"b\": \"2\",\n" + "\"c\": \"3\"\n" + "},\n" + "\"traceId\": \"202209162226011001\",\n" + "\"traceTime\": \"20220916222601\",\n" + "\"txCode\": \"POST\"\n" + "}";
        try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
            client.start();
            final SimpleHttpRequest request = SimpleRequestBuilder.post(url).addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).build();
            request.setBody(reqMsg, ContentType.APPLICATION_JSON);

            Future<SimpleHttpResponse> future = client.execute(request, new FutureCallback<SimpleHttpResponse>() {
                @Override
                public void completed(SimpleHttpResponse simpleHttpResponse) {
                    log.info("...SimpleHttpRequest.completed");
                    String msg = simpleHttpResponse.getBodyText();
                    log.debug("msg:{}", msg);
                }

                @Override
                public void failed(Exception e) {
                    log.error("...SimpleHttpRequest.failed:{}", e.getMessage());
                }

                @Override
                public void cancelled() {
                    log.info("...SimpleHttpRequest.cancelled");
                }
            });

            SimpleHttpResponse response = future.get();

            log.info("response.status[{}]", response.getCode());
            log.info("response.reasonPhrase[{}]", response.getReasonPhrase());

        } catch (Exception e) {
            log.error("{} 异常:{}", e.getMessage());
        }
    }
}


