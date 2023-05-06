package nohi.boot.junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>基础单元测试内容</p>
 * @date 2023/01/10 21:45
 **/
public class DemoUtilsTest {

    @BeforeEach
    void setUp() {
        System.out.println("setUp");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @Test
    public void testEqualsAndNotEquals() {
        // set up
        DemoUtils demoUtils = new DemoUtils();
        int expected = 6;
        // execute
        int actual = demoUtils.intAdd(2, 4);
        // assert
        Assertions.assertEquals(expected, actual, "2+4 must be 6");
    }

    /**
     * 检查对你是否为空
     */
    @Test
    public void testAssertNull(){
        // set up
        DemoUtils demoUtils = new DemoUtils();
        String str1 = null;
        String str2 = "NOHI";

        // execute && assert
        Assertions.assertNull(demoUtils.checkNull(str1), "Object should be null");
        Assertions.assertNotNull(demoUtils.checkNull(str2), "Object should not be null");
    }

    @Test
    void intAdd() {
        DemoUtils u = new DemoUtils();

        Assertions.assertEquals(6, u.intAdd(1, 5), "1+5 must equals 6");
        Assertions.assertNotEquals(5, u.intAdd(1, 5), "1+5 must not equals 5");
    }
}
