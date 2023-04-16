package nohi.ms.sysuser.web;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import nohi.ms.sys.user.dto.userquery.UserQueryReq;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 *
 * @author NOHI
 * 2022-07-11 13:46
 **/
@Tag(name = "User")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private int demoCount = 10;

    @GetMapping("/user-nums")
    public Integer getUserNums() {
        return new Random().nextInt(100);
    }

    /**
     * {"id": 123, "name": "张三", "age": 20, "sex": "male"}
     *
     * @author NOHI
     * @date 2023/4/2 20:19
     */
    @GetMapping("/user/{id}")
    public UserDTO getUserInfo(@PathVariable("id") int id) {
        UserDTO user = new UserDTO();
        user.setUserId("id_" + id);
        user.setAge(20);
        user.setUserName("zhangsan");
        user.setAddress("male");
        return user;
    }

    @Operation(summary = "lists", description = "用户列表")
    @GetMapping(value = "/lists")
    public List<UserDTO> lists() {
        log.info("users.lists...");
        List<UserDTO> list = Lists.newArrayList();
        for (int i = 0; i < demoCount; i++) {
            UserDTO dto = UserDTO.builder().userId("U_" + i).userNo("" + i).userName("NAME_" + i).build();
            list.add(dto);
        }
        return list;
    }

    @PostMapping(value = "/lists")
    public List<UserDTO> lists(@RequestBody @Valid UserQueryReq req) {
        log.debug("用户列表:{}", JSONObject.toJSONString(req));

        List<UserDTO> list = Lists.newArrayList();
        for (int i = 0; i < demoCount; i++) {
            UserDTO dto = UserDTO.builder().userId("U_" + i).userNo("" + i).userNo("NAME_" + i).build();
            list.add(dto);
        }
        return list;
    }

    @Operation(summary = "lists-sleep", description = "用户列表,服务sleep指定秒")
    @GetMapping(value = "/lists-sleep")
    public List<UserDTO> listsWithSleep(Integer sleep) throws InterruptedException {
        log.info("users.lists...sleep[{}]", sleep);
        if (null != sleep && sleep > 0) {
            TimeUnit.SECONDS.sleep(sleep);
        } else {
            sleep = 0;
        }
        if (sleep == 1) {
            throw new RuntimeException("异常返回");
        }
        List<UserDTO> list = Lists.newArrayList();
        for (int i = 0; i < sleep; i++) {
            UserDTO dto = UserDTO.builder().userId("U_" + i).userNo("" + i).userName("NAME_" + i).build();
            list.add(dto);
        }
        log.info("users.lists...return data size[{}]", list.size());
        return list;
    }
}
