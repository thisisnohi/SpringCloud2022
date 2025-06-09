package nohi.redis.pubsub;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2025/05/11 15:35
 **/
@Slf4j
public class PrintMessageReceiver {

    public PrintMessageReceiver() {

    }
//    public PrintMessageReceiver(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }

    // 1. 最通用的签名（推荐首先尝试）
    public void handleMessage(Object message) {
        log.info("==> Received: " + message);
    }

    // 2. 字符串消息签名

    public void handleMessage(String message) {
        log.info("==> String message: " + message);
    }

    // 3. 字节数组签名
    public void handleMessage(byte[] message) {
        log.info("==> Bytes length: " + message.length);
    }

    // 4. 完整Message对象签名
    public void handleMessage(Message message, byte[] pattern) {
        log.info("==> Full message from: " + new String(message.getChannel()));
    }
}
