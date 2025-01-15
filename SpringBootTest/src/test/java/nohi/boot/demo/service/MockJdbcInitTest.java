package nohi.boot.demo.service;

import lombok.extern.slf4j.Slf4j;
import nohi.boot.SpringBootTestApplication;
import nohi.boot.demo.dao.TbUserMapper;
import nohi.boot.demo.entity.TbUser;
import nohi.boot.demo.service.TbUserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>数据库初始化</p>
 **/
@Slf4j
@SpringBootTest(classes = SpringBootTestApplication.class)
@DisplayName("数据库初始化")
public class MockJdbcInitTest {

    static int id = 10000;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private TbUserService service;


    @BeforeEach
    public void beforeEach() {
        log.info("===>beforeEach ===> 准备参数");
        jdbcTemplate.execute("INSERT INTO `t_user` VALUES (" + (id + 1) + ", '张三', '男', 'aaaa', '12314@qq.com');");
        jdbcTemplate.execute("INSERT INTO `t_user` VALUES (" + (id + 2) + ", '李四', '男', 'aaaa', '12314@qq.com');");
        jdbcTemplate.execute("INSERT INTO `t_user` VALUES (" + (id + 4) + ", '王五', '男', 'aaaa', '12314@qq.com');");
        jdbcTemplate.execute("INSERT INTO `t_user` VALUES (" + (id + 5) + ", '赵六', '男', 'aaaa', '12314@qq.com');");
        jdbcTemplate.execute("INSERT INTO `t_user` VALUES (" + (id + 6) + ", '宋七', '男', 'aaaa', '12314@qq.com');");
    }

    @AfterEach
    public void afterEach(){
        log.info("===>afterEach ===> 清理现场");
        jdbcTemplate.execute("delete from t_user where id >" + id);
    }

    /**
     * 查询初始化数据
     */
    @DisplayName("查询初始化数据")
    @Test
    public void getInitData() {
        TbUser tbUser = service.queryById(id + 1);
        Assertions.assertNotNull(tbUser, (id+1) + " must exists");
    }

    /**
     * 查询初始化数据
     */
    @DisplayName("@sql初始化数据")
    @Sql("/db/02_data_20000.sql")
    @Test
    public void sqlInit() {
        Map<String, Object> map = new HashMap<>();
        map.put("idMin", 20000);
        List<TbUser> list = service.selectByExample(map);
        Assertions.assertEquals(5, list.size(), "id >= 20000 数据量为5");
    }
}
