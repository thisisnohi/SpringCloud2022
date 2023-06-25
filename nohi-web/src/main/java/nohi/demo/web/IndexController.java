package nohi.demo.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>index</p>
 * @date 2023/06/18 16:50
 **/
@Controller
@Tag(name = "Index")
@RequestMapping("index")
@Slf4j
public class IndexController {
    @RequestMapping("")
    public String abc() {
        log.info("==>index");
        return "index.html";
    }
}
