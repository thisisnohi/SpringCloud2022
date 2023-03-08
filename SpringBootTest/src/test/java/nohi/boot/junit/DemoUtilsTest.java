package nohi.boot.junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/01/10 21:45
 **/
class DemoUtilsTest {

    @BeforeEach
    void setUp() {
        System.out.println("setUp");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @Test
    void intAdd() {
        DemoUtils u = new DemoUtils();

        Assertions.assertEquals(6, u.intAdd(1,5), "1+5 must equals 6");
        Assertions.assertNotEquals(5, u.intAdd(1,5), "1+5 must not equals 5");
    }
}