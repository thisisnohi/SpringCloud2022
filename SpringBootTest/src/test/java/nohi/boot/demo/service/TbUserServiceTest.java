package nohi.boot.demo.service;

import nohi.boot.demo.entity.TbUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/01/13 13:07
 **/
@SpringBootTest
class TbUserServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    TbUserService userService;

    @Test
    public void queryAll() {
        userService.queryAll().forEach(System.out::println);
    }

    /**
     * 批量插入数据-不回滚
     */
    @Test
    public void add() {
        List<TbUser> users = new ArrayList<>();
        for (int i = 0; i < 15; ++i) {
            TbUser user = TbUser.builder().id(i + 1).name("test" + i).sex(i % 2 == 0 ? "男" : "女").pwd("aaaa").email("123" + i + "@qq.com").build();
            users.add(user);
        }
        users.forEach(System.out::println);
        userService.add(users);
    }

    /**
     * 批量插入数据-回滚
     */
    @Test
    @Transactional
    public void batchInserAndRollback() {
        List<TbUser> users = new ArrayList<>();
        for (int i = 0; i < 1; ++i) {
            TbUser user = TbUser.builder().id(i + 11).name("test" + i).sex(i % 2 == 0 ? "男" : "女").pwd("aaaa").email("123" + i + "@qq.com").build();
            users.add(user);
        }
        users.forEach(System.out::println);
        userService.add(users);

        // 打印所有数据
        userService.queryAll().forEach(System.out::println);
    }

    @Test
    public void queryByIds() {
        userService.queryByIds(Set.of(1, 2, 3));
        userService.queryByName("test1").forEach(System.out::println);
        userService.queryByName2("test1").forEach(System.out::println);
        TbUser user = userService.queryById(TbUser.builder().id(2).build());
        System.out.println(user);
        userService.queryByNameMap("test1").forEach(System.out::println);
        System.out.println(userService.count());
    }

    @Test
    public void query() {
        userService.queryAll().forEach(System.out::println);
        userService.queryByName("test1").forEach(System.out::println);
        userService.queryByName2("test1").forEach(System.out::println);
        TbUser user = userService.queryById(TbUser.builder().id(2).build());
        System.out.println(user);
        userService.queryByNameMap("test1").forEach(System.out::println);
        System.out.println(userService.count());
    }

    @Test
    public void delete() {
        TbUser user = TbUser.builder().id(2).build();
        userService.deleteById(user);

        userService.deleteBy("name", "test15");

        userService.deleteByIds();

    }

    @Test
    public void change() {
        TbUser user1 = TbUser.builder().name("蔡徐坤").build();
        userService.changeBy(user1, "sex", "男");

        user1.setName("蔡徐坤2");
        userService.changeUserById(user1);
    }
}