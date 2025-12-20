package nohi.netty.httpserer;

import com.google.common.collect.Maps;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import nohi.demo.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>httpserver</p>
 * @date 2024/04/24 16:46
 **/
public class TestHttpServer {

    @Test
    public void server() throws InterruptedException {
        Consumer<? super HttpServerRoutes> routesBuilder = routes -> {
            String url = "/get";
            Charset charset = Charset.defaultCharset();
            Map<String, String> headers = Maps.newHashMap();
            routes.get(url, (request, response) -> {
                Map<String, Object> map = new ConcurrentHashMap<>();
                request.requestHeaders().iteratorAsString().forEachRemaining(action -> map.put(action.getKey(), action.getValue()));
                map.put("url", url);
                Map<String, List<String>> queryParams = new QueryStringDecoder(request.uri(), charset).parameters();
                Mono<byte[]> flux = Mono.just(Collections.unmodifiableMap(queryParams))
                        .map(JsonUtils::toString)
                        .log("nohi.netty.httpserver.get.req")
                        .map(data -> data.getBytes(charset))
                        .map(data -> {
                            return "Hellow World!".getBytes(charset);
                        })
                        .map(data -> new String(data, charset))
                        .log("nohi.netty.httpserver.get.resp")
                        .map(data -> data.getBytes(charset));

                headers.forEach(response::addHeader);

                return response.sendByteArray(flux).neverComplete();
            });
        };

        HttpServer httpServer = HttpServer.create().port(8080)
                .doOnChannelInit((observer, channel, remoteAddress) -> {
                    channel.pipeline().addFirst(new LoggingHandler("nohi.netty", LogLevel.INFO));
                })
                .doOnConnection(conn -> {
                    conn.addHandlerFirst(new ReadTimeoutHandler(10, TimeUnit.SECONDS));
                })
                .accessLog(true)
                .route(routesBuilder)
                .compress(true)
                .wiretap(true);

        DisposableServer disposableServer = httpServer.bindNow();

        TimeUnit.SECONDS.sleep(1000);
        // 关闭
        disposableServer.dispose();
    }
}
