package nohi.http;

import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import nohi.web.utils.HttpClientPoolUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试 httpclient 连接池工具
 *
 * @author NOHI
 * @date 2022/8/25 22:16
 **/
@Slf4j
public class TestApacheHttpClientPool {

    /**
     * 初始化:
     *  打印默认级别
     */
    static {
//        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLoggerList().forEach(logger -> logger.setLevel(Level.DEBUG));
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLoggerList().forEach(logger -> {
            if (null != logger && null != logger.getLevel()) {
                System.out.println(logger.getName() + ":" + logger.getLevel().levelStr);
            }
        });
    }

    /**
     * 测试httpclient连接池，请求响应
     */
    @Test
    public void testHttpClient() {
        CloseableHttpClient client = HttpClientPoolUtils.getHttpClient();
        log.debug("debug");
        log.info("INFO");
        log.warn("warn");
        log.error("error");
        String url = "http://www.baidu.com";
        String msg = HttpClientPoolUtils.doGetRequest(client, url);
        System.out.println("msg:" + msg);
    }

    @Test
    public void testHttpClientPool() throws InterruptedException {
        int totalThread = 10;
        int total = 10000;
        long methodStart = System.currentTimeMillis();

        CountDownLatch cd = new CountDownLatch(total);
        ExecutorService es = Executors.newFixedThreadPool(totalThread);
        // 只需要一个client,如果在循环中创建client，可能出现大量client对象
        CloseableHttpClient client = HttpClientPoolUtils.getHttpClient();
        for (int i = 0; i < total; i++) {
            int finalI = i;
            es.execute(() -> {
                String title = String.format("请求[%s]", "" + finalI);
                long start = System.currentTimeMillis();
                String url = "http://127.0.0.1:8888/mock/http";
                try {

                    String msg = HttpClientPoolUtils.doGetRequest(client, url);
                    log.info("{} over taketime[{}]", title, (System.currentTimeMillis() - start));
                    log.debug("{} over taketime[{}]", title, msg);
                } catch (Exception e) {
                    System.out.println(title + "异常:" + e.getMessage());
                }
                cd.countDown();
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    log.error("sleep异常:{}", e.getMessage());
//                }
            });
        }
        boolean await = cd.await(30, TimeUnit.SECONDS);
        log.info("======= over... ======== await[{}] 耗时[{}]", await, (System.currentTimeMillis() - methodStart));
    }
}
