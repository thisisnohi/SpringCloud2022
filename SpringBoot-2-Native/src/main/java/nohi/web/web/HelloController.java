package nohi.web.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;

/**
 * Created by nohi on 2018/10/16.
 */
@RestController
public class HelloController {
    Logger log = LoggerFactory.getLogger(HelloController.class);

    @GetMapping(value = {"", "hello"})
    public String index() {
        return "Welcome to reactive world ~...";
    }

//    @GetMapping(value = {"webflux"})
//    public Mono<String> webflux() {
//        return Mono.just("Welcome to webflux world ~");
//    }

    @GetMapping(value = {"run"})
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
