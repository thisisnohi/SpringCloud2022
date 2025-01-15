package nohi.demo.springnative.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>SpringNative-demo</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2022/10/26 17:34
 **/
@RestController
@RequestMapping("/hellodemo")
public class HelloDemoController {

    @GetMapping("/")
    public String index(String msg) {
        System.out.println("msg:" + msg);
        return "Hey " + msg;
    }
}
