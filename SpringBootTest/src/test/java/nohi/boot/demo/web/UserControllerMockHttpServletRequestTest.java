package nohi.boot.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
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
 * @description <p>WEB Controller测试-MockHttpServletRequest</p>
 * @date 2023/05/29 21:30
 **/
@Slf4j
@DisplayName("WEB Controller测试-MockHttpServletRequest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerMockHttpServletRequestTest {

    @Autowired
    private UserController helloController;

    @Autowired
    private MockMvc mockMvc;

    private static MockHttpServletRequest request;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
        request.addParameter("name", "张三");
        request.addParameter("sex", "男");
        request.addParameter("mail", "zs@163.com");
    }

    @DisplayName("MVC返回JSON")
    @Test
    public void addUserTest() throws Exception {
        // 调用
        MockHttpServletResponse mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .param("name", request.getParameterValues("name"))
                        .param("sex", request.getParameterValues("sex"))
                        .param("mail", request.getParameterValues("mail"))
                )
                .andDo(MockMvcResultHandlers.print())
                // 返回状态是否正确
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        mvcResult.setCharacterEncoding("UTF-8");
        log.info("mvcResult:{}", mvcResult.getContentAsString());
    }
}
