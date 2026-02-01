package nohi.demo.web.kakfa;


import com.esotericsoftware.minlog.Log;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.service.kafka.KafkaMessageProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>kafka</p>
 * @date 2026/01/28 20:59
 **/
@RequestMapping("/kafka")
@Tag(name = "Kafka", description = "demo for Some project...")
@RestController
@Slf4j
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
