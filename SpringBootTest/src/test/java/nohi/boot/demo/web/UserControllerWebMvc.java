package nohi.boot.demo.web;

import nohi.boot.demo.service.HelloService;
import nohi.boot.demo.service.TbUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebMvcTest
public class UserControllerWebMvc {

    @Autowired
    private UserController helloController;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Autowired
    private TbUserService tbUserService;
    @MockBean
    @Autowired
    private HelloService helloService;


    @Test
    public void testNotNull() {
        Assertions.assertThat(helloController).isNotNull();
        System.out.println("======testNotNull=======");
    }

    @Test
    public void testHello() throws Exception {
        System.out.println("======testHello=======");
        Mockito.when(helloService.sayHello(Mockito.anyString())).thenReturn("Mock hello");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hello/spring")).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Mock hello"));
        System.out.println("======testHello end=======");
    }
}
