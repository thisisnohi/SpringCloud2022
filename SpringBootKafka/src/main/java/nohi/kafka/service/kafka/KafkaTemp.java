package nohi.kafka.service.kafka;


import java.util.HashMap;
import java.util.Map;

/**
 * <h3>SpringBootKafka</h3>
 *
 * @author NOHI
 * @description <p>临时类</p>
 * @date 2026/02/02 22:03
 **/
public class KafkaTemp {
    // 生产时间
    public static Map<String, Long> producerMap = new HashMap<String, Long>();
    // 消费时间
    public static Map<String, Long> consumerMap = new HashMap<String, Long>();

    public static void clear(){
        producerMap.clear();
        consumerMap.clear();
    }
}
