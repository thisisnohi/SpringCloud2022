package nohi.redis.pubsub;


import lombok.extern.slf4j.Slf4j;
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
    private RedisTemplate redisTemplate;

    public PrintMessageReceiver(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void receiveMessage(MessageVo messageDto, String channel) {
        // 接收的topic
        log.info("==> channel:{}", channel);
        log.info("==> message:{}", messageDto.getTitle());
    }

}
