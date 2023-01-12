package nohi.boot.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>MockitoDemo</p>
 * @date 2023/01/12 12:51
 **/
@ExtendWith(MockitoExtension.class)
public class Mockito2Demo {
    @Mock
    private Random random;
    @BeforeEach
    public void before() {
        System.out.println("===BeforeEach===");
    }

    /**
     * 增加jvm参数：--add-exports=java.base/jdk.internal.util.random=ALL-UNNAMED
     */
    @Test
    public void testNew() {
        when(random.nextInt()).thenReturn(100);
        Assertions.assertEquals(100, random.nextInt());
    }


    @Test
    public void test() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt()).thenThrow(new RuntimeException("异常1"), new RuntimeException("异常2"));

        try {
            mockRandom.nextInt();
            Assertions.fail();//上一行会抛出异常，到catch中去，走不到这里
        } catch (Exception ex) {
            System.out.println("===ex：" + ex.getMessage());
            Assertions.assertTrue(ex instanceof RuntimeException);
            Assertions.assertEquals("异常1", ex.getMessage());
        }
        try {
            mockRandom.nextInt();
            Assertions.fail();
        } catch (Exception ex) {
            Assertions.assertTrue(ex instanceof RuntimeException);
            Assertions.assertEquals("异常2", ex.getMessage());
        }
    }
}
