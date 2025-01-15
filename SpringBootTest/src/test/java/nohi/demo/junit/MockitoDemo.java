package nohi.demo.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
public class MockitoDemo {

    /**
     * jvm 添加： --add-exports=java.base/jdk.internal.util.random=ALL-UNNAMED
     */
    @Test
    public void testOld() {
        Random mockRandom = mock(Random.class); //mock了一个Random对象
        Assertions.assertEquals(0, mockRandom.nextInt());//未进行打桩，每次返回值都是0

        when(mockRandom.nextInt()).thenReturn(100);  // 进行打桩操作，指定调用 nextInt 方法时，永远返回 100
        Assertions.assertEquals(100, mockRandom.nextInt());
    }

    @Mock
    private Random random;

    @BeforeEach
    public void before() {
        System.out.println("===BeforeEach===");
        // 让注解生效
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNew() {
        when(random.nextInt()).thenReturn(100);
        Assertions.assertEquals(100, random.nextInt());
    }
}
