package nohi.kafka.service.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>KafkaMessageProducer</p>
 * @date 2026/01/28 20:21
 **/
@Slf4j
@Component
public class KafkaMessageProducer {
    // 注入KafkaTemplate(Spring自动配置)
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // 同步发送消息
    public void sendSyncMessage(String topic, String message) {
        String title = String.format("[%s][同步消息]", topic);

        try {
            SendResult<String, String> result = kafkaTemplate.send(topic, message).get();
            log.debug("{} topic[{}] partition[{}] offset[{}] message[{}]", title, result.getRecordMetadata().topic(), result.getRecordMetadata().partition(), result.getRecordMetadata().offset(), message);
        } catch (Exception e) {
            log.error("{} 异常:{}", title, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    // 异步发送消息
    public void sendAsyncMessage(String topic, String message) {
        String title = String.format("[%s][异步消息]", topic);
        log.debug("{} {}", title, message);
        try {
            CompletableFuture<SendResult<String, String>> rsFuture = kafkaTemplate.send(topic, message);
            log.debug("{} send...", title);
            rsFuture.whenComplete((result, e) -> {
                log.debug("{} topic[{}] partition[{}] offset[{}] message[{}]", title, result.getRecordMetadata().topic(), result.getRecordMetadata().partition(), result.getRecordMetadata().offset(), message);
            });
        } catch (Exception e) {
            log.error("{} 异常:{}", title, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    // 异步发送消息
    public void batchAsyncMessage(String topic, Integer batchSize) {
        String title = String.format("[%s][异步消息] 批次数量:%s", topic, batchSize);
        String message = "";
        // 清理缓存
        KafkaTemp.clear();
        long start = System.currentTimeMillis();
        log.info("{} start", title);
        String key = null;
        for (int i = 0; i < batchSize; i++) {
            key = start + "_" + i;
            // KafkaTemp.producerMap.put(key, System.currentTimeMillis());
            kafkaTemplate.send(topic, key, "" + System.currentTimeMillis());
        }
        long end = System.currentTimeMillis();
        log.info("{} end 耗时:{}", title, (end - start));
    }

}
