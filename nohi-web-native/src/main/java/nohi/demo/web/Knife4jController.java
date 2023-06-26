package nohi.demo.web;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.dto.hello.HelloReq;
import nohi.demo.dto.hello.HelloResp;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * Created by nohi on 2018/10/16.
 */
@Slf4j
@RestController
@Tag(name = "knife4j", description="DEMO for Knife4j")
@RequestMapping("/knife4j")
public class Knife4jController {

    @Operation(method = "method", summary = "返回字符串")
    @GetMapping(value = {"", "index"})
    public String index() {
        return "Welcome to reactive world ~...";
    }

//    @Operation(method = "webflux", summary = "webflux返回字符串")
//    @GetMapping(value = {"webflux"})
//    public Mono<String> webflux() {
//        return Mono.just("Welcome to webflux world ~");
//    }


    @GetMapping(value = {"run"})
    @Operation(tags = "TAGS", summary = "@operation tags属性可以单独定义一个一级元素")
    public String runTime(Integer time) {
        int count = 0;
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count >= time) {
                break;
            }
            count++;
        }
        return "time is over " + time;
    }

    @PostMapping("sayHello")
    @Operation(summary = "向某人打招呼", description = "向某人打招呼, 演示Operation/Schema用法")
    public HelloResp sayHello(@RequestBody @Valid HelloReq req){
        log.info("请求对象:{}", JSONObject.toJSONString(req));
        HelloResp resp = new HelloResp();
        BeanUtils.copyProperties(req, resp);
        resp.setRetCode("SUC");
        resp.setRetMsg("响应信息..." + req.getName());
        return resp;
    }
}
