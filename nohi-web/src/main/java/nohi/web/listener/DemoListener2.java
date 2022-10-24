package nohi.web.listener;

import nohi.web.event.DemoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2022/10/24 17:25
 **/
@Component
public class DemoListener2 {
    @EventListener
    public void onApplicationEvent(DemoEvent demoEvent) {
        System.out.println(">>>>>>>>>DemoListener2>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("收到了：" + demoEvent.getSource() + "消息;时间：" + demoEvent.getTimestamp());
        System.out.println("消息：" + demoEvent.getId() + ":" + demoEvent.getMessage());
    }
}
