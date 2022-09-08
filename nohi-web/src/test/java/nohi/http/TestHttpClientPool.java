package nohi.http;

import nohi.web.utils.HttpClientPoolUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;

/**
 * 测试 httpclient 连接池工具
 *
 * @author NOHI
 * @date 2022/8/25 22:16
 **/
public class TestHttpClientPool {

    @Test
    public void testPool() {
        CloseableHttpClient client = HttpClientPoolUtils.getHttpClient();
        String url = "http://www.baidu.com";
        String msg = HttpClientPoolUtils.doGetRequest(client, url);
        System.out.println("msg:" + msg);

    }
}
