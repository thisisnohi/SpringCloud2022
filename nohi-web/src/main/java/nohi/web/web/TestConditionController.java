package nohi.web.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import nohi.common.config.BizConfig;
import nohi.socket.njserver.NjSocketService;
import nohi.socket.zshserver.SoketService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-07-06 19:16
 **/
@Api(tags = "TCP服务")
@RestController
@ConditionalOnProperty(prefix = "test", name = "condition.conditioncontroller", havingValue = "true")
@Slf4j
public class TestConditionController implements InitializingBean {
    @Value("${testStr:testString}")
    private String testStr;

    @Autowired
    private BizConfig testConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("====afterPropertiesSet===testStr:{}", testStr);
    }

    @ApiOperation("开启websocket")
    @GetMapping(value = {"startSocket"})
    public String startSocket() {
        int port = testConfig.getZsh().getPort();
        log.debug("port:{}", port);

        try {
            SoketService.getInstanse(port).start();
        } catch (Exception e) {
            log.error("开启socket异常:{}", e.getMessage(), e);
            return "开启socket异常" + e.getMessage();
        }

        return "开启socket成功，端口[" + port + "]";
    }

    @ApiOperation("开启NjSocket")
    @GetMapping(value = {"开启NjSocket"})
    public String starNjtSocket() {
        int port = testConfig.getNj().getPort();
        log.debug("port:{}", port);
        try {
            NjSocketService.getInstanse(port).start();
        } catch (Exception e) {
            log.error("开启socket异常:{}", e.getMessage(), e);
            return "开启socket异常" + e.getMessage();
        }

        return "开启socket成功，端口[" + port + "]";
    }
}
