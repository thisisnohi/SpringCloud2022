package nohi.demo.web;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.dto.hello.HelloReq;
import nohi.demo.dto.hello.HelloResp;
import nohi.demo.service.Knife4jService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * Knife4j
 * @author NOHI
 * @date 2023/8/20 09:57
 */
@Slf4j
@RestController
@Tag(name = "knife4j&Valid", description = "DEMO for Knife4j and Valid 参数校验")
@RequestMapping("/knife4j")
public class Knife4jController {
    @Autowired
    private Knife4jService knife4jService;

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
    @Operation(summary = "@Valid", description = "演示Operation/Schema用法 + @Valid 参数校验")
    public HelloResp sayHello(@Valid @RequestBody HelloReq req) {
        log.info("请求对象:{}", JSONObject.toJSONString(req));
        HelloResp resp = new HelloResp();
        BeanUtils.copyProperties(req, resp);
        resp.setRetCode("SUC");
        resp.setRetMsg("响应信息..." + req.getName());
        return resp;
    }

    @PostMapping("sayHello2")
    @Operation(summary = "@Validated", description = "@Validated 校验，程序中判断是否校验通过，返回约定格式报文")
    public HelloResp sayHello2(@Validated @RequestBody HelloReq req, BindingResult results) {
        log.info("请求对象:{}", JSONObject.toJSONString(req));

        HelloResp resp = new HelloResp();
        BeanUtils.copyProperties(req, resp);
        // 把实体注解中的错误信息返回
        if (results.hasErrors()) {
            resp.setRetCode("ERROR");
            resp.setRetMsg("响应信息..." + results.getFieldError().getDefaultMessage());
            return resp;
        }
        resp.setRetCode("SUC");
        resp.setRetMsg("响应信息..." + req.getName());
        return resp;
    }

    @PostMapping("sayHello3")
    @Operation(summary = "service层参数校验", description = "service层参数校验")
    public HelloResp sayHello3(@RequestBody HelloReq req, BindingResult results) {
        log.info("请求对象:{}", JSONObject.toJSONString(req));
        return knife4jService.sayHello3(req);
    }
}
