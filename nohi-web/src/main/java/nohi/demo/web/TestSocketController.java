package nohi.demo.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Socket
 * @author NOHI
 * @date 2023/8/24 21:51
 */
@Tag(name = "socket")
@RestController
@Slf4j
@RequestMapping("/socket")
public class TestSocketController {

    @Operation(summary = "start")
    @GetMapping(value = {"/start/{port}"})
    public void testHttpClient(@PathVariable("port") Integer port) {
        log.info("准备启动[{}]服务", port);




    }
}
