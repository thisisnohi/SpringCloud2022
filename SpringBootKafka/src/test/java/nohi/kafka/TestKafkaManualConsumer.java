package nohi.kafka;


import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>Kafka消费-手工连接</p>
 * @date 2026/01/25 10:36
 **/
@DisplayName("Kafka-手工开启-生产者")
@Slf4j
public class TestKafkaManualConsumer {
    // Kafka集群地址（单机：localhost:9092；集群：node1:9092,node2:9092,node3:9092）
    private static final String BOOTSTRAP_SERVERS = "10.0.0.210:9092";
//    private static final String BOOTSTRAP_SERVERS = "10.0.0.181:9092,10.0.0.182:9092,10.0.0.183:9092";
    // 要发送的Topic名称（需提前创建，或配置自动创建）
    private static final String TOPIC_NAME = "test_topic";
    // 消费者组ID（同一组的消费者会负载均衡消费Topic）
    private static final String GROUP_ID = "test_consumer_group";

    private static final String TITLE = "[Kafka-" + TOPIC_NAME + "][消费]";


    @DisplayName("消费")
    @Test
    public void consumer() {
        // 1. 配置消费者参数
        Properties props = new Properties();
        // 必选配置：Kafka集群地址
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        // 必选配置：消费者组ID（必填，除非配置独立消费者）
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        // 必选配置：key/value的反序列化方式（与生产者序列化对应）
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 每个消费者实例设置唯一的ID（如机器IP+端口、容器ID等）
        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "consumer-NotRepeat");

        // 可选配置：自动提交偏移量（默认true，间隔5秒）
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); // 关闭自动提交，手动控制
        // props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000"); // 自动提交间隔
        // 可选配置：初始偏移量策略（earliest=从头消费，latest=从最新消费）
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 核心优化配置 ↓↓↓
        // 1. 最大等待时间：从默认500ms改为100ms，缩短拉取间隔（越小越实时）
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "100");
        // 2. 单次拉取最大消息数：从默认500改为50，限制单次读取量
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "50");
        // 3. 最小拉取数据量：保持默认1B（优先按时间触发，而非数据量）
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "1");

        // 2. 创建KafkaConsumer实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        try {
            // 3. 订阅Topic（可订阅多个，如Collections.singletonList("topic1", "topic2")）
            consumer.subscribe(Collections.singletonList(TOPIC_NAME));

            System.out.println("开始消费消息（按Ctrl+C停止）...");
            // 4. 轮询拉取消息（消费者核心逻辑，必须持续轮询）
            int index = 0;
            while (true) {
                index++;
                if (index % 500 == 0) {
                    log.info("{}", index++);
                }
                // 拉取消息：超时时间1秒，无消息则返回空
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10));
//                log.info("{} records[{}]", index, null == records ? "null" : records.count());
                // 5. 处理拉取到的消息
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("消费消息：topic=%s, partition=%d, offset=%d, key=%s, value=%s [%s]%n",
                            record.topic(), record.partition(), record.offset(),
                            record.key(), record.value(), DateUtil.now());
                }

                // 6. 手动提交偏移量（确保消息处理完成后提交，避免重复消费）
                if (!records.isEmpty()) {
                    // 方式1：同步提交（阻塞等待提交完成）
                    consumer.commitSync();
                    // System.out.println("偏移量已手动提交！");

                    // 方式2：异步提交（非阻塞，加回调）
                    /*
                    consumer.commitAsync((offsets, exception) -> {
                        if (exception != null) {
                            System.err.println("异步提交偏移量失败：" + exception.getMessage());
                        } else {
                            System.out.println("异步提交偏移量成功：" + offsets);
                        }
                    });
                    */
                }
            }
        } catch (WakeupException e) {
            System.out.println("消费者被唤醒，准备退出...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7. 关闭前提交最后一次偏移量，避免数据丢失
            consumer.commitSync();
            consumer.close();
            System.out.println("消费者已关闭！");
        }
    }
}
