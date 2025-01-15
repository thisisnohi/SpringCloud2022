package nohi.http;

import nohi.demo.utils.OkHttpUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 测试OK http
 * @author NOHI
 * @date 2022/8/25 22:28
 **/
public class TestOkHttp {

    @Test
    public void testOkhttp() throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url = "https://www.baidu.com";
        String msg = OkHttpUtils.getInstance().getData(url).body().string();
        System.out.println("msg:" + msg);
        System.out.println("=============================");
        msg = OkHttpUtils.getInstance().postJson(url, "{}");
        System.out.println("msg:" + msg);
    }
}
