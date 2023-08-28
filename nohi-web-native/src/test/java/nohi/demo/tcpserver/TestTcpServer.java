package nohi.demo.tcpserver;

import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>测试TcpServer</p>
 * @date 2023/08/24 22:00
 **/
@Slf4j
public class TestTcpServer {

    @Test
    public void startTcpServer() {
        TcpServer tcpServer = TcpServer.create()
                .port(8888)
                .wiretap(true)
                .doOnConnection(conn -> conn.addHandler(new ReadTimeoutHandler(5, TimeUnit.SECONDS)))
                .handle((inbound, outbound) -> {
                    // PooledUnsafeDirectByteBuf
                    int msgLenth = 4;


                    Flux rs = Flux.empty();
                    Mono<byte[]> mono = Mono.empty();
                    // reduce
                    // .take(Duration.of(2, TimeUnit.SECONDS.toChronoUnit()))
                    mono = inbound.receive().asByteArray().take(Duration.of(2, TimeUnit.SECONDS.toChronoUnit()))
                            .reduce((a, b) -> {
                                log.info("============>a:{} b:{}", a.getClass(), b.getClass());
                                log.info("============>a[{}] b[{}]", a.length, b.length);
                                byte[] result = new byte[a.length + b.length];
                                System.arraycopy(a, 0, result, 0, a.length);
                                System.arraycopy(b, 0, result, a.length, b.length);
                                return result;
                            }).map(data -> {
                                log.info("AAAAAAAAAAAAAAAAA");
                                String str = new String(data) + "++++++ this is over";
                                return str.getBytes();
                            });
//                            mono.subscribe(data -> {
//                                log.info("=======subscribe111>data:{}", data);
//                                log.info("=======subscribe111>data:{}", new String(data));
//                            });
                    return outbound.sendByteArray(mono).neverComplete();

                });// 开启线路记录（wire logging）用来检查点对点的流量
//                        .doOnChannelInit((observer, channel, remoteAddress) ->
//                                channel.pipeline().addFirst(new LoggingHandler(TestTcpServer.class.getName()))) //<2>
//<1>
        log.info("start ...");
        DisposableServer server =
                tcpServer
                        .bindNow();
        log.info("started ...");
        server.onDispose()
                .block();

    }

}
