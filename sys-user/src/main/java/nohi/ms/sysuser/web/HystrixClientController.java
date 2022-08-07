package nohi.ms.sysuser.web;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hystrix客户端
 *
 * @author NOHI
 * 2022-08-06 20:09
 **/
@Api(tags = "HystrixClient")
@RestController
@RequestMapping("/hystrix")
@Slf4j
public class HystrixClientController {
}
