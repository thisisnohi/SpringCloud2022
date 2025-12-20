package nohi.demo.service.enent;

import nohi.demo.event.DemoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>DemoPublisher</p>
 * @date 2022/10/24 16:14
 **/
@Component
public class DemoEventPublisher {

    private final ApplicationContext applicationContext;

    @Autowired
    public DemoEventPublisher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void publish(long id, String message) {
        applicationContext.publishEvent(new DemoEvent(this, id, message));
    }

}
