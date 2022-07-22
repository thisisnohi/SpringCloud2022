package nohi.ms.sysuser.web;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import nohi.ms.sys.user.dto.userquery.UserQueryReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户
 *
 * @author NOHI
 * 2022-07-11 13:46
 **/
@Api(tags = "User")
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @ApiOperation(value = "lists", notes = "用户列表")
    @GetMapping(value = "/lists")
    public List<UserDTO> lists() {
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
}
