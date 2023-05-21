package nohi.boot.demo.dao;

import lombok.extern.slf4j.Slf4j;
import nohi.boot.SpringBootTestApplication;
import nohi.boot.demo.entity.TbUser;
import nohi.boot.demo.service.TbUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>Mock Dao</p>
 * @date 2023/05/21 21:45
 **/
@Slf4j
@SpringBootTest(classes = SpringBootTestApplication.class)
public class MockDaoTest {
    TbUser firstUser;
    @Mock
    private TbUserMapper mapper;
    @InjectMocks
    private TbUserService service;

    @BeforeEach
    public void beforeEach() {
        log.info("==>beforeEach");
        firstUser = TbUser.builder().build();
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
}
