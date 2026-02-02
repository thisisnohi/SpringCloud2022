package nohi.kafka.web;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nohi.kafka.service.kafka.KafkaMessageProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>SpringBootKafka</h3>
 *
 * @author NOHI
 * @description <p>kafka</p>
 * @date 2026/01/31 21:36
 **/
@RestController
@RequestMapping("/kafka")
@Slf4j
@Tag(name = "Kafka", description = "demo for Kafka...")
public class KafkaController {
    private final KafkaMessageProducer producer;

    public KafkaController(KafkaMessageProducer producer) {
        this.producer = producer;
    }

    @Operation(summary = "同步消息", description = "向kafka发送消息，同步")
    @GetMapping("/send")
    public String sendSyncMessage(@RequestParam String topic, @RequestParam String msg) {
        log.info("同步send[{}] msg:{}", topic, msg);
        producer.sendSyncMessage(topic, msg);
        return "消息发送完成：" + msg;
    }

    @Operation(summary = "异步消息", description = "向kafka发送消息，异步")
    @GetMapping("/sendAsync")
    public String sendAsyncMessage(@RequestParam String topic, @RequestParam String msg) {
        log.info("异步send[{}] msg:{}", topic, msg);
        producer.sendAsyncMessage(topic, msg);
        return "消息发送中：" + msg;
    }


    @Operation(summary = "异步批量", description = "向kafka发送消息，异步批量")
    @GetMapping("/batchAsyncMessage")
    public String batchAsyncMessage(@RequestParam String topic, @RequestParam Integer batchSize) {
        log.info("异步send[{}] 批次数量:{}", topic, batchSize);
        producer.batchAsyncMessage(topic, batchSize);
        return "消息发送中：" + batchSize;
    }
}
