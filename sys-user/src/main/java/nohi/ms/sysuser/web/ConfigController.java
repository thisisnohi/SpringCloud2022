package nohi.ms.sysuser.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sysuser.config.SysUserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置
 *
 * @author NOHI
 * 2022-08-06 20:09
 **/
@Tag(name = "ConfigController")
@RestController
@RequestMapping("/config")
@Slf4j
public class ConfigController {
    @Autowired
    private SysUserConfig sysUserConfig;

    @Operation(summary = "sysuser config", description = "获取sysuser配置")
    @GetMapping(value = "/")
    public String index() {
        return sysUserConfig.toString();
    }
}
