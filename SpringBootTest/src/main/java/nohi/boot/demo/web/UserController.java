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

import java.util.HashMap;
import java.util.Map;

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

    @ApiOperationSupport(author = "thisisnohi@163.com")
    @Operation(summary = "获取所有user")
    @GetMapping("/list/{idMin}")
    public Object listUsers(@PathVariable("idMin") Integer idMin) {
        logger.info("===listUsers===");
        Map<String, Object> map = new HashMap<>();
        map.put("idMin", idMin);
        return userService.selectByExample(map);
    }

    @Operation(summary = "保存用户")
    @PostMapping("/save")
    public TbUser saveUser(@RequestBody TbUser user) {
        logger.info("保存User:{}", JSONObject.toJSONString(user));
        return userService.saveUser(user);
    }

    @Operation(summary = "保存用户")
    @PostMapping("/add")
    public TbUser addUser(String name, String sex, String mail) {
        TbUser user = TbUser.builder().build();
        user.setName(name);
        user.setSex(sex);
        user.setEmail(mail);
        user.setPwd("000000");
        logger.info("保存User:{}", JSONObject.toJSONString(user));
        return userService.saveUser(user);
    }

    @Operation(summary = "向用户{user}问好")
    @GetMapping("/hello/{user}")
    public String sayHello(@PathVariable("user") String user) {
        logger.info("===sayHello===");
        return helloService.sayHello(user);
    }

    @Operation(summary = "测试jakarta事务")
    @GetMapping("/test-transaction/{id}")
    public TbUser testTransaction(@PathVariable("id") Integer id) throws Exception {
        logger.info("===testTransaction===:{}", id);
        return userService.testTransaction(id);
    }

    @Operation(summary = "测试spring事务")
    @GetMapping("/test-transactionspring/{id}")
    public TbUser testTransactionSpring(@PathVariable("id") Integer id) throws Exception {
        logger.info("===testTransactionSpring===:{}", id);
        return userService.testTransaction2(id);
    }

    @Operation(summary = "测试默认事务")
    @GetMapping("/test-tc-default/{id}")
    public TbUser testTransactionDefault(@PathVariable("id") Integer id) throws Exception {
        logger.info("===testTransactionSpring===:{}", id);
        return userService.testTransactionDefault(id);
    }
}
