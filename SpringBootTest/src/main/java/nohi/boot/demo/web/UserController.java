package nohi.boot.demo.web;

import nohi.boot.demo.service.HelloService;
import nohi.boot.demo.service.TUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>User</p>
 * @date 2023/01/13 13:15
 **/
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    TUserService userService;
    @Autowired
    HelloService helloService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("users")
    public Object users() {
        logger.info("===users===");
        return userService.queryAll();
    }

    @GetMapping("/hello/{user}")
    public String sayHello(@PathVariable("user") String user) {
        logger.info("===users===");
        return helloService.sayHello(user);
    }
}
