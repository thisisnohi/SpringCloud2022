package nohi.web.web.page;

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
@RequestMapping("index")
public class IndexController {
    @RequestMapping("")
    public String abc() {
        return "index.html";
    }
}
