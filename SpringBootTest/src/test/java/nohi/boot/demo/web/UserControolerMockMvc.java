package nohi.boot.demo.web;

import nohi.boot.demo.service.HelloService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>mock</p>
 * @date 2023/01/16 21:30
 **/
@SpringBootTest
@AutoConfigureMockMvc
public class UserControolerMockMvc {

    @Autowired
    private UserController helloController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testNotNull() {
        Assertions.assertThat(helloController).isNotNull();
    }

    @Test
    public void testHello1() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hello/spring")).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Hello spring"));
    }

}
