package nohi.ms.sysuser.web;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import nohi.ms.sys.user.dto.userquery.UserQueryReq;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 *
 * @author NOHI
 * 2022-07-11 13:46
 **/
@Api(tags = "User")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @ApiOperation(value = "lists", notes = "用户列表")
    @GetMapping(value = "/lists")
    public List<UserDTO> lists() {
        log.info("users.lists...");
        List<UserDTO> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            UserDTO dto = UserDTO.builder().userId("U_" + i).userNo("" + i).userName("NAME_" + i).build();
            list.add(dto);
        }
        return list;
    }

    @RequestMapping(value = "/lists")
    public List<UserDTO> lists(@RequestBody UserQueryReq req) {
        log.debug("用户列表:{}", JSONObject.toJSONString(req));

        List<UserDTO> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            UserDTO dto = UserDTO.builder().userId("U_" + i).userNo("" + i).userNo("NAME_" + i).build();
            list.add(dto);
        }
        return list;
    }

    @ApiOperation(value = "lists-sleep", notes = "用户列表,服务sleep指定秒")
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
