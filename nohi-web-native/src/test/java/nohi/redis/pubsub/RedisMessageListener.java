package nohi.redis.pubsub;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>消息监听 </p>
 * @date 2025/05/11 15:26
 **/
@Slf4j
public class RedisMessageListener implements MessageListener {
    private RedisTemplate redisTemplate;

    public RedisMessageListener(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 接收的topic
        log.info("1 channel:{}", new String(pattern));

        //序列化对象（特别注意：发布的时候需要设置序列化；订阅方也需要设置序列化）
        MessageVo messageDto = (MessageVo) redisTemplate.getValueSerializer().deserialize(message.getBody());
        if (null != messageDto) {
            log.info(messageDto.getTitle() + "," + messageDto.getContent());
        } else {
            log.warn("message is null");
        }
    }
}
