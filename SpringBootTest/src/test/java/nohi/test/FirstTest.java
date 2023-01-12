package nohi.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/01/12 12:26
 **/
@SpringBootTest
public class FirstTest {
    @Test
    public void test() {
        int a = 1;
        Assertions.assertEquals(1, a);//判断二者是否相等
    }

}
