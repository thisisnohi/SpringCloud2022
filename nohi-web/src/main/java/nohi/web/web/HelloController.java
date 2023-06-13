package nohi.web.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by nohi on 2018/10/16.
 */
@RestController
@Tag( name= "HelloController")
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
    @Operation(tags = "runTime")
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
            count ++;
        }
        return "over";
    }


    @GetMapping(value = {"runTimes"})
    public String runTimes(Integer times) {
        int count = 0;
        while (true) {
            //
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count >= times) {
                break;
            }
            count ++;
        }
        return "over";
    }

}
