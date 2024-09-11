package nohi.boot.demo.dao;

import lombok.extern.slf4j.Slf4j;
import nohi.boot.SpringBootTestApplication;
import nohi.boot.demo.entity.TbUser;
import nohi.boot.demo.service.TbUserService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>Mock Dao</p>
 * @date 2023/05/21 21:45
 **/
@Slf4j
@SpringBootTest(classes = SpringBootTestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Mock Bean 和 Exception异常")
public class MockBeanTest {
    TbUser firstUser;
    @MockBean
    private TbUserMapper mapper;
    @Autowired
    private TbUserService service;

    @BeforeEach
    public void beforeEach() {
        log.info("==>beforeEach");
        firstUser = new TbUser();
        firstUser.setId(1);
        firstUser.setName("NOHI");
        firstUser.setSex("MAN");
        firstUser.setPwd("********");
        firstUser.setEmail("thisisnohi@163.com");
    }

    /**
     * Mock Dao
     * InjectMocks 注入到Service中
     */
    @DisplayName("When & Verify")
    @Test
    @Order(1)
    public void mockDao() {
        Mockito.when(mapper.selectById(1)).thenReturn(firstUser);
        TbUser user = service.queryById(1);
        Assertions.assertNotNull(user, "User[1] not null");
        Assertions.assertEquals("NOHI", user.getName(), "User[1].name === NOHI");

        /** 验证mapper.selectById是否被执行 **/
        Mockito.verify(mapper).selectById(1);
        // 以下方法未执行，注释去除后，验证报错
        //Mockito.verify(mapper).selectList(null);
        /** 验证mapper.selectById执行次数 **/
        Mockito.verify(mapper, Mockito.times(1)).selectById(1);
        Mockito.verify(mapper, Mockito.times(0)).selectList(null);
    }

    @DisplayName("测试异常验证")
    @Test
    @Order(2)
    public void throwRuntimeError() {
        Mockito.doThrow(new RuntimeException("TEST")).when(mapper).insert(null);

        /** 新增正常对象不报错 **/
        Assertions.assertDoesNotThrow(() -> {
            service.add(firstUser);
        });

        /** 新增空对象报错 **/
        TbUser nullObj = null;
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.add(nullObj);
        });
    }

    @DisplayName("测试不同场景")
    @Test
    @Order(3)
    public void testMocks() {
        Mockito.doThrow(new RuntimeException("TEST")).when(mapper).insert(null);

        Mockito.when(mapper.insert(firstUser)).thenThrow(new RuntimeException("NOHI")).thenReturn(1);

        /** 新增对象报错 **/
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.add(firstUser);
        });

        /** 正常返回 **/
        Assertions.assertEquals(1, service.add(firstUser));


    }
}








