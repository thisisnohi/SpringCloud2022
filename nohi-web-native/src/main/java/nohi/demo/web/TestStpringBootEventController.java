package nohi.demo.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.dto.intf.IntfReq;
import nohi.demo.dto.intf.IntfResp;
import nohi.demo.service.enent.DemoEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * ContentType： 告诉服务器当前发送的数据是什么格式
     * Accept ： 用来告诉服务器，客户端能认识哪些格式,最好返回这些格式
     * --consumes 用来限制ContentType
     * --produces 用来限制Accept
     *
     * @param req IntfReq
     * @return IntfResp
     */
    @Operation(summary = "发布消息", description = "发布消息")
    @PostMapping(value = "/publish", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public IntfResp<Object> publish(@RequestBody IntfReq<String> req) {
        log.info("请求对象:{}", req);
        demoEventPublisher.publish(System.currentTimeMillis(), req.getBody());
        IntfResp<Object> resp = IntfResp.buildResp(req);
        resp.setBody(req.getBody() + " 消息已经发送成功");
        resp.initRespTime();
        log.info("响应对象:{}", resp);
        return resp;
    }
}
