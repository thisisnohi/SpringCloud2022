package nohi.web.web.page;

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
@RequestMapping("model-attribute")
public class ModelAttributeController {

    @ModelAttribute
    public void modelAttribute(@RequestParam(required = false) String a, Model model) {
        model.addAttribute("a", a);
    }

    @RequestMapping("")
    public String index(Model model) {
        return "index.html";
    }
}
