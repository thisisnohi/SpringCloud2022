package nohi.web.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import nohi.web.service.enent.DemoEventPublisher;
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
@Api(tags = "SpringBootEvent测试")
@RestController
@RequestMapping("/testSpringBootEvent")
@Slf4j
public class TestStpringBootEventController {

    @Autowired
    private DemoEventPublisher demoEventPublisher;

    @ApiOperation(value = "发布事件", notes = "发布事件")
    @GetMapping("/publish")
    public Map<String, String> publish(String msg) {
        demoEventPublisher.publish(System.currentTimeMillis(), msg);
        return null;
    }
}
