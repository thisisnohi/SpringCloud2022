package nohi.web.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Created by nohi on 2018/10/16.
 */
@RestController
@Tag(name = "HelloController")
@RequestMapping("/hello")
public class HelloController {


    @GetMapping(value = {"", "hello"})
    public String index() {
        return "Welcome to reactive world ~...";
    }

    @GetMapping(value = {"webflux"})
    public Mono<String> webflux() {
        return Mono.just("Welcome to webflux world ~");
    }


    @GetMapping(value = {"run"})
    @Operation(tags = "runTime", summary = "运行指定时间")
    public String runTime(Integer time) {
        int count = 0;
        while (true) {
            try {
                Thread.sleep(1000);
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
