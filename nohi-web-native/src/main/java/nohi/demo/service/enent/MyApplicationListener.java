package nohi.demo.service.enent;

import lombok.extern.slf4j.Slf4j;
import nohi.demo.event.DemoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/06/26 13:52
 **/
@Slf4j
@Service
public class MyApplicationListener {
    @EventListener
    public void onApplicationEvent(DemoEvent event) {
        log.info("接收到事件消息:{}", event);
    }
}
