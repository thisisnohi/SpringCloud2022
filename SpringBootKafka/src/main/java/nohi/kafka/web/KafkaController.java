package nohi.kafka.web;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nohi.kafka.service.kafka.KafkaMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/send")
    public String sendSyncMessage(@RequestParam String msg) {
        log.info("send msg:{}", msg);
        producer.sendSyncMessage("test_topic", msg);
        return "消息发送中：" + msg;
    }
    @GetMapping("/sendAsync")
    public String sendAsyncMessage(@RequestParam String msg) {
        log.info("send msg:{}", msg);
        producer.sendAsyncMessage("test_topic", msg);
        return "消息发送中：" + msg;
    }

}
