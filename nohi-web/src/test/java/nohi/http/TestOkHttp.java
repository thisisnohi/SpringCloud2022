package nohi.http;

import nohi.demo.utils.OkHttpUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 测试OK http
 * @author NOHI
 * @date 2022/8/25 22:28
 **/
public class TestOkHttp {

    @Test
    public void testOkhttp() throws IOException, URISyntaxException {
        String url = "http://www.baidu.com";
        String msg = OkHttpUtils.getInstance().getData(url).body().string();
        System.out.println("msg:" + msg);

        msg = OkHttpUtils.getInstance().postJson(url, "{}");
        System.out.println("msg:" + msg);
    }
}
