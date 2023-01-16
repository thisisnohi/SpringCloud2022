package nohi.boot.demo.service;

import nohi.boot.demo.entity.TUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/01/13 13:07
 **/
@SpringBootTest
class TUserServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    TUserService userService;

    @Test
    public void queryAll() {
        userService.queryAll().forEach(System.out::println);
    }

    @Test
    public void add() {
        List<TUser> users = new ArrayList<>();
        for (int i = 0; i < 15; ++i) {
            TUser user = TUser.builder().id(i + 1).name("test" + i).sex(i % 2 == 0 ? "男" : "女").pwd("aaaa").email("123" + i + "@qq.com").build();
            users.add(user);
        }
        users.forEach(System.out::println);
        userService.add(users);
    }

    @Test
    public void query() {
        userService.queryAll().forEach(System.out::println);
        userService.queryByName("test1").forEach(System.out::println);
        userService.queryByName2("test1").forEach(System.out::println);
        TUser user = userService.queryById(TUser.builder().id(2).build());
        System.out.println(user);
        userService.queryByNameMap("test1").forEach(System.out::println);
        System.out.println(userService.count());
    }

    @Test
    public void delete() {
        TUser user = TUser.builder().id(2).build();
        userService.deleteById(user);

        userService.deleteBy("name", "test15");

        userService.deleteByIds();

    }

    @Test
    public void change() {
        TUser user1 = TUser.builder().name("蔡徐坤").build();
        userService.changeBy(user1, "sex", "男");

        user1.setName("蔡徐坤2");
        userService.changeUserById(user1);
    }
}