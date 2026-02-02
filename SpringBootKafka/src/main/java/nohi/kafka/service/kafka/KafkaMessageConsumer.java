package nohi.kafka.service.kafka;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * <h3>SpringBootKafka</h3>
 *
 * @author NOHI
 * @description <p>消费者</p>
 * @date 2026/02/02 21:22
 **/
@Slf4j
@Component
public class KafkaMessageConsumer {
    // 监听指定Topic，groupId优先使用注解中的，无则用配置文件的
    @KafkaListener(topics = "springboot-test-topic", groupId = "springboot-kafka-group")
    public void consumeMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            // 处理消息
            log.debug("消费消息：topic={}, partition={}, offset={}, value={}", record.topic(), record.partition(), record.offset(), record.value());
            // 手动提交偏移量（确保消息处理完成后提交，避免重复消费）
            ack.acknowledge();
        } catch (Exception e) {
            // 处理失败可选择不提交偏移量，让消息重新消费
            log.error("消费消息失败:{}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "batch_topic", groupId = "springboot-kafka-group")
    public void consumeMessageBatchTopic(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            String key = record.key();
            Long start = KafkaTemp.producerMap.get(key);
            if (null != start) {
                Long end = System.currentTimeMillis();
                KafkaTemp.consumerMap.put(key, System.currentTimeMillis());
                log.debug("{} 耗时:{}", key, end - start);
            } else {
                log.debug("{} 未找到", key);
            }
            // 处理消息
            // log.debug("消费消息：topic={}, partition={}, offset={}, value={}", record.topic(), record.partition(), record.offset(), record.value());
            // 手动提交偏移量（确保消息处理完成后提交，避免重复消费）
        } catch (Exception e) {
            // 处理失败可选择不提交偏移量，让消息重新消费
            log.error("消费消息失败:{}", e.getMessage(), e);
        } finally {
            ack.acknowledge();
        }
    }
}
