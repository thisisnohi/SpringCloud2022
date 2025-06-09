package nohi.redis.pubsub;


import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

import java.util.concurrent.TimeUnit;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>Redisson 消息发布订阅</p>
 * @date 2025/05/11 14:57
 **/
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRedissionPubSub {
    /**
     * 发布订阅消息：主体1
     */
    public static String TOPIC_ONE = "TEST_TOPIC1";
    /**
     * 发布订阅消息：主体2
     */
    public static String TOPIC_TWO = "TEST_TOPIC2";

    private static RedissonClient redisson;

    @BeforeAll
    public static void init() {
        Config config = new Config();
        // TransportMode.EPOLL 只能在Linux下使用
        config.setTransportMode(TransportMode.NIO);
        // 设置编码方式
        config.setCodec(new StringCodec());
        // useSentinelServers 配置哨兵模式连接,address为哨兵端口
        // useClusterServers 集群模式
        // useSingleServer 单节点模式
        // useMasterSlaveServers 主从模式
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setPassword("123456")
        ;

        redisson = Redisson.create(config);
        RBucket<Object> obj = redisson.getBucket("bucket");
        obj.set("this is bucket in INIT");
        log.debug("obj:{}", obj);
        log.debug("obj:{}", obj.get());
    }

    @DisplayName("定时发布消息")
    @Test
    @Order(1)
    public void 发布消息() throws InterruptedException {
        int index = 0;
        RTopic topicOne = redisson.getTopic(TOPIC_ONE, new JsonJacksonCodec());
        RTopic topicTwo = redisson.getTopic(TOPIC_TWO, new JsonJacksonCodec());
        while (true) {
            index++;
            // 创建消息
            MessageVo msg = MessageVo.builder().id("TOP1" + index).title(TOPIC_ONE).content(DateUtil.now() + " " + index).build();
            topicOne.publish(msg);

            if (index % 2 == 0) {
                log.info("===>TOP2");
                msg = MessageVo.builder().id("TOP2" + index).title(TOPIC_TWO).content(DateUtil.now() + " " + index).build();
                topicTwo.publish(msg);
            }
            log.info("消息[{}] 发布", index);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @DisplayName("订阅消息")
    @Test
    @Order(2)
    public void 订阅消息() throws InterruptedException {
        RTopic topicOne = redisson.getTopic(TOPIC_ONE, new JsonJacksonCodec());
        RTopic topicTwo = redisson.getTopic(TOPIC_TWO, new JsonJacksonCodec());

        topicOne.addListener(MessageVo.class, new MessageListener<MessageVo>() {
            @Override
            public void onMessage(CharSequence channel, MessageVo msg) {
                log.info("TOPIC_ONE:{}", msg);
            }
        });

        topicTwo.addListener(MessageVo.class, new MessageListener<MessageVo>() {
            @Override
            public void onMessage(CharSequence channel, MessageVo msg) {
                log.info("TOPIC_TWO:{}", msg.toString());
            }
        });


        TimeUnit.SECONDS.sleep(30);
    }
}
