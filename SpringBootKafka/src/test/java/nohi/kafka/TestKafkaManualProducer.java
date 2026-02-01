package nohi.kafka;


import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>手工开启</p>
 * @date 2026/01/24 17:44
 **/
@DisplayName("Kafka-手工开启-生产者")
@Slf4j
public class TestKafkaManualProducer {
    // Kafka集群地址（单机：localhost:9092；集群：node1:9092,node2:9092,node3:9092）
    private static final String BOOTSTRAP_SERVERS = "10.0.0.210:9092";
//    private static final String BOOTSTRAP_SERVERS = "10.0.0.181:9092,10.0.0.182:9092,10.0.0.183:9092";
    // 要发送的Topic名称（需提前创建，或配置自动创建）
    private static final String TOPIC_NAME = "test_topic";

    private static final String TITLE = "[Kafka-" + TOPIC_NAME + "]";

    /**
     * 检查Topic是否已存在
     */
    private static boolean isTopicExists(AdminClient adminClient, String topicName) throws ExecutionException, InterruptedException {
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(false); // 不列出内部Topic（如__consumer_offsets）
        ListTopicsResult listTopicsResult = adminClient.listTopics(options);
        // 获取所有Topic名称并判断
        return listTopicsResult.names().get().contains(topicName);
    }

    @DisplayName("初化kafka")
    @BeforeAll
    public static void init() {
        log.info("{} init 初始化Topic", TITLE);
        // 1. 配置AdminClient参数
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        // 可选：连接超时时间
        props.put(AdminClientConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 10000);
        // 可选：请求超时时间
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);

        // 2. 创建AdminClient实例（try-with-resources自动关闭）
        try (AdminClient adminClient = AdminClient.create(props)) {
            // 检查Topic是否已存在
            if (isTopicExists(adminClient, TOPIC_NAME)) {
                log.debug("Topic " + TOPIC_NAME + " 已存在，无需创建！");
                return;
            }

            // 3. 配置Topic参数
            int partitions = 3; // 分区数（根据业务负载设置）
            short replicationFactor = 1; // 副本数（单机版只能设1，集群建议3）

            // Topic配置项（可选，按需添加）
            Map<String, String> topicConfigs = new HashMap<>();
            topicConfigs.put(TopicConfig.RETENTION_MS_CONFIG, "86400000"); // 消息保留时间1天
            topicConfigs.put(TopicConfig.CLEANUP_POLICY_CONFIG, "delete"); // 清理策略：删除（默认）
            topicConfigs.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1048576"); // 单条消息最大1MB

            // 4. 构建CreateTopicsRequest
            NewTopic newTopic = new NewTopic(TOPIC_NAME, partitions, replicationFactor).configs(topicConfigs);

            // 5. 发送创建Topic的请求（同步执行）
            CreateTopicsResult result = adminClient.createTopics(Collections.singleton(newTopic));
            // 阻塞等待创建结果
            result.all().get();

            log.debug("Topic " + TOPIC_NAME + " 创建成功！");

        } catch (InterruptedException | ExecutionException e) {
            log.error("{} 创建Topic失败:{}", TITLE, e.getMessage(), e);
            System.exit(1);
        }
    }

    @DisplayName("开启生产者")
    @Test
    public void startProducer() {
        log.info("{} startProducer", TITLE);
        // 1. 配置生产者参数
        Properties props = getProperties();

        // 2. 创建KafkaProducer实例（资源需关闭，建议用try-with-resources）
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            // 3. 发送10条测试消息（同步发送，可直观看到结果）
            int index = 0;
            while(true) {
                for (int i = 0; i < 10; i++) {
                    this.makeProducer(producer, "[" + index + "-" + i + "]");
                }
                if (index++ > 1000) {
                    break;
                }
                TimeUnit.SECONDS.sleep(10);
            }
            log.debug("所有消息发送完成！");
        } catch (Exception e) {
            log.error("{} 创建KafkaProducer实例:{}", TITLE, e.getMessage(), e);
        }

    }

    public void makeProducer(KafkaProducer<String, String> producer, String i) {
        // 构建消息：key可选（用于分区路由），value是消息内容
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME,                // Topic
                "key-" + i,               // 消息key
                "hello kafka - " + i + " - " + DateUtil.now()     // 消息value
        );

        // 方式1：同步发送（阻塞等待结果，适合关键消息）
        try {
            RecordMetadata metadata = producer.send(record).get();
            // 打印发送结果（分区、偏移量等）
            log.info("发送成功：topic={}, partition={}, offset={}, key={}, value={}", metadata.topic(), metadata.partition(), metadata.offset(), record.key(), record.value());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("发送失败：" + e.getMessage());
            log.error("{} 发送失败:{}", TITLE, e.getMessage(), e);
        }

        // 方式2：异步发送（非阻塞，通过回调处理结果）
                /*
                producer.send(record, (metadata, exception) -> {
                    if (exception != null) {
                        System.err.println("异步发送失败：" + exception.getMessage());
                    } else {
                        System.out.printf("异步发送成功：topic=%s, partition=%d, offset=%d%n",
                                metadata.topic(), metadata.partition(), metadata.offset());
                    }
                });
                */
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        // 必选配置：Kafka集群地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        // 必选配置：key/value的序列化方式（需与消费者反序列化对应）
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 可选配置：消息确认机制（1=leader确认即可，all=所有副本确认，0=不确认）
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        // 可选配置：重试次数（消息发送失败时重试）
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 可选配置：批量发送大小（16KB）
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 可选配置：延迟发送（1ms，凑批量）
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        return props;
    }

}
