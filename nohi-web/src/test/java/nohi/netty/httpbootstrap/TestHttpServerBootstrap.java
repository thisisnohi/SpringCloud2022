package nohi.netty.httpbootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.http.server.reactive.HttpHandler;

import java.util.concurrent.TimeUnit;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>ServerBootstrap</p>
 * @date 2024/04/25 14:24
 **/
public class TestHttpServerBootstrap {
    int port = 8080;

    @Test
    public void server() throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
                        System.out.println("initChannel ch:" + ch);
                        ch.pipeline()
                                // 1 HttpRequestDecoder，用于解码request
                                .addLast("decoder", new HttpRequestDecoder())
                                // 2 HttpResponseEncoder，用于编码response
                                .addLast("encoder", new HttpResponseEncoder())
                                // 3 aggregator，消息聚合器（重要）。
                                // 为什么能有FullHttpRequest这个东西，就是因为有他，HttpObjectAggregator，
                                // 如果没有他，就不会有那个消息是FullHttpRequest的那段Channel，同样也不会有FullHttpResponse。
                                // 如果我们将HttpObjectAggregator(512 * 1024)的参数含义是消息合并的数据大小，如此代表聚合的消息内容长度不超过512kb。
                                .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                                // 4 添加我们自己的处理接口
                                .addLast("handler", new HttpServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        b.bind(port).sync();

        TimeUnit.SECONDS.sleep(1000);
    }
}
