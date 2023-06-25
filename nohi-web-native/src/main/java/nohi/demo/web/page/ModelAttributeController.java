package nohi.demo.web.page;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>ModelAttribute</p>
 * @date 2023/06/18 16:50
 **/
@Controller
@Tag(name = "ModelAttribute")
@RequestMapping("model-attribute")
@Slf4j
public class ModelAttributeController {

    @ModelAttribute
    public void modelAttribute(@RequestParam(required = false) String a, Model model) {
        log.info("ModelAttribute a:{}", a);
        model.addAttribute("a", a);
    }

    @RequestMapping("")
    public String index(Model model) {
        log.info("==>index a:{}", model.getAttribute("a"));
        return "index.html";
    }
}
