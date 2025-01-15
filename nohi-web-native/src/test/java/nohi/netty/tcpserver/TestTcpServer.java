package nohi.netty.tcpserver;

import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
                .wiretap(true)  // 开启线路记录
                .doOnConnection(conn -> conn.addHandler(new ReadTimeoutHandler(15, TimeUnit.SECONDS)))
                .handle((inbound, outbound) -> {
                    // 计数器
                    AtomicInteger i = new AtomicInteger();
                    // 完整报文
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    Mono<byte[]> mono = inbound.receive().asByteArray()
                            .takeUntil(data -> {
                                log.info("takeUntil..[{}]:{}", i.get(), data);
                                try {
                                    bos.write(data);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                // 获取长度

                                // 判断长度报文长度是否完整  完成后返回true
                                // TODO 需要按规则修改判断
                                if (i.getAndIncrement() == 1) {
                                    return true;
                                } else {
                                    return false;
                                }
                            })
                            .map(data -> {
                                log.info("===>接收到数据:{}", data);
                                return data;
                            })
                            .reduce((a, b) -> {
                                log.info("============合并请求数据>a[{}] b[{}]", a.length, b.length);
                                byte[] result = new byte[a.length + b.length];
                                System.arraycopy(a, 0, result, 0, a.length);
                                System.arraycopy(b, 0, result, a.length, b.length);
                                return result;
                            })
                            .map(data -> {
                                log.info("===>处理数据:[{}]{}", data.length, new String(data));
                                String str = new String(data) + "++++++ this is over";
                                outbound.sendString(Mono.just(str + "===========")).then();
                                return str.getBytes();
                            })
                            .log("xxxxxxxxxxxx")
                    ;
                    return outbound.sendByteArray(mono).neverComplete();
                });
        // 开启线路记录（wire logging）用来检查点对点的流量
//                        .doOnChannelInit((observer, channel, remoteAddress) ->
//                                channel.pipeline().addFirst(new LoggingHandler(TestTcpServer.class.getName()))) //<2>
//<1>
        log.info("start ...");
        DisposableServer server = tcpServer.bindNow();
        log.info("started ...");
        server.onDispose().block();
    }

}
