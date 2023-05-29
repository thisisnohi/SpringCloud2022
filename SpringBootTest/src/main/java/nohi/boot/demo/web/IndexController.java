package nohi.boot.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/01/12 12:32
 **/
@Controller
@RequestMapping("/")
public class IndexController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = {"/", "/index"})
    public String index() {
        logger.info("===index===");
        return "/index";
    }
}
