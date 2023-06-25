package nohi.demo.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.service.enent.DemoEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>测试SpringbootEvent</p>
 * @date 2022/10/24 16:16
 **/
@Tag(name = "SpringBootEvent测试")
@RestController
@RequestMapping("/testSpringBootEvent")
@Slf4j
public class TestStpringBootEventController {

    @Autowired
    private DemoEventPublisher demoEventPublisher;

    @Operation(summary = "发布事件", description = "发布事件")
    @GetMapping("/publish")
    public Map<String, String> publish(String msg) {
        demoEventPublisher.publish(System.currentTimeMillis(), msg);
        return null;
    }
}
