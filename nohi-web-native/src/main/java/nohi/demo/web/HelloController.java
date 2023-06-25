package nohi.demo.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * Created by nohi on 2018/10/16.
 */
@RestController
@Tag(name = "HELLO", description="DEMO for Knife4j")
@RequestMapping("/hello")
public class HelloController {

    @Operation(method = "method", summary = "返回字符串")
    @GetMapping(value = {"", "index"})
    public String index() {
        return "Welcome to reactive world ~...";
    }

    @GetMapping(value = {"webflux"})
    public Mono<String> webflux() {
        return Mono.just("Welcome to webflux world ~");
    }


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
        return "over";
    }
}
