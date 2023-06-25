package nohi.demo.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>Spring Boot Event</p>
 * @date 2022/10/24 16:09
 **/
@Getter
@Setter
public class DemoEvent extends ApplicationEvent {
    private Long id;
    private String message;

    public DemoEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }
}
