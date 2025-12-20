package nohi.redis.pubsub;


import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>RedisTemplate消息发布</p>
 * @date 2025/05/11 14:57
 **/
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRedisTemplatePub {
    /**
     * 发布订阅消息：主体1
     */
    public static String TOPIC_ONE = "TEST_TOPIC1";
    /**
     * 发布订阅消息：主体2
     */
    public static String TOPIC_TWO = "TEST_TOPIC2";

    private static RedisTemplate<String, String> template;
    private static LettuceConnectionFactory lcf;

    @BeforeAll
    public static void init() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("127.0.0.1");
        configuration.setPort(6379);
        configuration.setPassword("123456");

        lcf = new LettuceConnectionFactory(configuration);
        lcf.afterPropertiesSet();
        template = new RedisTemplate<>();
        template.setConnectionFactory(lcf);
        // 设置key的序列化器
        template.setKeySerializer(RedisSerializer.string());
        // 设置value的序列化器
        template.setValueSerializer(RedisSerializer.json());
        // 设置hash key的序列化器
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置hash value的序列化器
        template.setHashValueSerializer(RedisSerializer.json());
        template.afterPropertiesSet();
        log.info("RedisTemplate init");
    }

    @DisplayName("定时发布消息")
    @Test
    @Order(1)
    public void 发布消息() throws InterruptedException {
        int index = 0;
        while (true) {
            index++;

            // 创建消息
            MessageVo msg = MessageVo.builder().id("TOP1" + index).title(TOPIC_ONE).content(DateUtil.now() + " " + index).build();
             template.convertAndSend(TOPIC_ONE, msg);

            if (index % 2 == 0) {
                log.info("===>TOP2");
                msg = MessageVo.builder().id("TOP2" + index).title(TOPIC_TWO).content(DateUtil.now() + " " + index).build();
                template.convertAndSend(TOPIC_TWO, msg);
            }
            log.info("消息[{}] 发布", index);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @DisplayName("订阅消息")
    @Test
    @Order(2)
    public void 订阅消息() throws InterruptedException {
        RedisMessageListener listener = new RedisMessageListener(template);

        PrintMessageReceiver receiver = new PrintMessageReceiver();
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver);
        adapter.setDefaultListenerMethod("handleMessage"); // 明确指定方法名
        adapter.setSerializer(RedisSerializer.json());
        /** adapter必须增加afterPropertiesSet方法调用 **/
        adapter.afterPropertiesSet();


        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 监听所有库的key过期事件
        container.setConnectionFactory(lcf);
        // 所有的订阅消息，都需要在这里进行注册绑定,new PatternTopic(TOPIC_NAME1)表示发布的主题信息
        // 可以添加多个 messageListener，配置不同的通道
        container.addMessageListener(listener, new PatternTopic(TOPIC_ONE));
        // new PatternTopic("pattern.*") 模糊匹配
        container.addMessageListener(adapter, new ChannelTopic(TOPIC_TWO));
        // 临时添加
//        container.addMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message, byte[] pattern) {
//                System.out.println("Raw message received: " + new String(message.getBody()));
//            }
//        }, new ChannelTopic(TOPIC_ONE));
//        container.addMessageListener((message, pattern) -> {
//            System.out.println("==>Raw channel: " + new String(message.getChannel()));
//            System.out.println("==>Raw body: " + Arrays.toString(message.getBody()));
//        }, new ChannelTopic(TOPIC_ONE));

        // 可选配置
        container.setTaskExecutor(Executors.newFixedThreadPool(4)); // 自定义线程池
        container.setErrorHandler(e -> System.err.println("Listener error: " + e.getMessage()));

        // 初始化容器
        container.afterPropertiesSet(); // 重要！启动容器
        container.start(); // 显式启动
        TimeUnit.SECONDS.sleep(30);
    }
}
