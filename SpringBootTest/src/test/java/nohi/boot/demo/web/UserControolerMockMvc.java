package nohi.boot.demo.web;

import nohi.boot.demo.service.HelloService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>mock</p>
 * @date 2023/01/16 21:30
 **/

@DisplayName("WEB Controller测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControolerMockMvc {

    @Autowired
    private UserController helloController;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("controller是否为空")
    @Test
    public void testNotNull() {
        Assertions.assertThat(helloController).isNotNull();
    }

    @DisplayName("MVC返回值测试")
    @Test
    public void controllerRespMsg() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hello/spring"))
                .andDo(MockMvcResultHandlers.print())
                // 返回状态是否正确
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello spring"));
    }

    @DisplayName("MVC返回页面")
    @Test
    public void controllerRespPage() throws Exception {
        // 调用
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                // 返回状态是否正确
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "/index");
    }
}
