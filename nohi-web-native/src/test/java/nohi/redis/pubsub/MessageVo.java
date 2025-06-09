package nohi.redis.pubsub;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>消息</p>
 * @date 2025/05/11 15:00
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageVo implements Serializable {
    private String id;
    private String title;
    private String content;
}
