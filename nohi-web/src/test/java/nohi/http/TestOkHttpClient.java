package nohi.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 测试 Ok Http
 *
 * @author NOHI
 * @date 2022/9/19 18:01
 */
@Slf4j
public class TestOkHttpClient {

    /**
     * 仅记录参数
     */
    @SuppressWarnings("CommentedOutCode")
    @Test
    public void testParam(){
        // 仅记录参数
        System.out.println("====仅记录参数=====");
        /*
        client = new OkHttpClient.Builder()
                .dispatcher(new Dispatcher(executorService))
                .connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDurationMills, TimeUnit.MILLISECONDS))
                .readTimeout(readTimeoutMills, TimeUnit.MILLISECONDS)
                .connectTimeout(connectTimeoutMills, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeoutMills, TimeUnit.MILLISECONDS)
                .protocols(Util.immutableList(Protocol.HTTP_1_1))
                .connectionSpecs(Arrays.asList(TLS_SPEC, ConnectionSpec.CLEARTEXT))
                .build();
         */
    }
    /**
     * Get请求
     *
     * @throws InterruptedException 异常
     */
    @Test
    public void testGet() throws IOException {
        String url = "http://127.0.0.1:8099/mock/1.json";
        // 1.创建okhttp客户端
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(1000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        // 2.无请求数据
        // 3.创建请求
        Request request = new Request.Builder().url(url).get().build();
        // 4.调用同步api
        Response response = client.newCall(request).execute();

        log.info("status:{}", response.code());
        log.info("响应报文:{}", Objects.requireNonNull(response.body()).string());
    }

    /**
     * 异步Get请求
     *
     * @throws InterruptedException
     */
    @Test
    public void testGetAsyn() throws InterruptedException {
        String url = "http://127.0.0.1:8888/mock/http?sleep=1000";

        ConnectionPool connectionPool = new ConnectionPool(5,5*60,TimeUnit.SECONDS);
        // 1.创建okhttp客户端
        OkHttpClient client = new OkHttpClient.Builder().connectionPool(connectionPool).readTimeout(1000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        // 2.创建请求
        Request request = new Request.Builder().url(url).get().build();
        // 3.调用异步请求
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error("交易失败:{}", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                log.info("返回信息:{}", Objects.requireNonNull(response.body()).string());
            }
        });
        log.info("================================");
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void post() throws IOException {
        String url = "http://127.0.0.1:8888/mock/http?sleep=1000";
        String reqMsg = "{\n" +
                "\"retCode\": \"\",\n" +
                "\"retMsg\": \"\",\n" +
                "\"data\": {\n" +
                "\"a\": \"这是请求\",\n" +
                "\"b\": \"2\",\n" +
                "\"c\": \"3\"\n" +
                "},\n" +
                "\"traceId\": \"202209162226011001\",\n" +
                "\"traceTime\": \"20220916222601\",\n" +
                "\"txCode\": \"POST\"\n" +
                "}";
        // 1.创建okhttp客户端
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(1000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        // 2.请求数据体
        RequestBody requestBody = RequestBody.create(reqMsg, MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE));
        // 3.创建请求
        Request request = new Request.Builder().url(url).post(requestBody).build();
        // 4.调用同步api
        Response response = client.newCall(request).execute();

        log.info("status:{}", response.code());
        log.info("响应报文:{}", Objects.requireNonNull(response.body()).string());
    }
}


