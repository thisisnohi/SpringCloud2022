package nohi.ms.sysuser.web;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import nohi.ms.sysuser.config.SysUserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 配置
 *
 * @author NOHI
 * 2022-08-06 20:09
 **/
@Api(tags = "ConfigController")
@RestController
@RequestMapping("/config")
@Slf4j
public class ConfigController {
    @Autowired
    private SysUserConfig sysUserConfig;

    @ApiOperation(value = "sysuser config", notes = "获取sysuser配置")
    @GetMapping(value = "/")
    public String index() {
        return sysUserConfig.toString();
    }
}
