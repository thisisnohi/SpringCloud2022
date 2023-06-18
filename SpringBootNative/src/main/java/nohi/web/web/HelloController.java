package nohi.web.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nohi on 2018/10/16.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {


    @GetMapping(value = {"", "hello"})
    public String index() {
        return "Welcome to hello world ~...";
    }

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
            count++;
        }
        return "over";
    }
}
