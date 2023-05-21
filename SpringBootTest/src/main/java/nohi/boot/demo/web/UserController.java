package nohi.boot.demo.web;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nohi.boot.demo.entity.TbUser;
import nohi.boot.demo.service.HelloService;
import nohi.boot.demo.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>User</p>
 * @date 2023/01/13 13:15
 **/

@Tag(name = "User API 服务")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    TbUserService userService;
    @Autowired
    HelloService helloService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperationSupport(author = "thisisnohi@163.com")
    @Operation(summary = "获取所有user")
    @GetMapping("/all")
    public Object users() {
        logger.info("===users===");
        return userService.queryAll();
    }

    @Operation(summary  = "保存用户")
    @GetMapping("/save")
    public TbUser saveUser(@RequestBody TbUser user) {
        logger.info("保存User:{}", JSONObject.toJSONString(user));
        return userService.saveUser(user);
    }

    @Operation(summary  = "向用户{user}问好")
    @GetMapping("/hello/{user}")
    public String sayHello(@PathVariable("user") String user) {
        logger.info("===sayHello===");
        return helloService.sayHello(user);
    }
}
