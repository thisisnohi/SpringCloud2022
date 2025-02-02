package nohi.demo.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;


/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>基础单元测试内容</p>
 * @date 2023/01/10 21:45
 **/
@Slf4j
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("DemoUtilsTest DEMO")
public class DemoUtilsTest {
    static DemoUtils demoUtils = null;
    @BeforeAll
    static void beforeAll() {
        log.info("==beforeAll===");
        demoUtils = new DemoUtils();
    }

    @AfterAll
    static void afterAll() {
        log.info("==afterAll===");
    }

    @BeforeEach
    void beforeEach() {
        log.info("==beforeEach===");
    }

    @AfterEach
    void afterEach() {
        log.info("==afterEach===");
    }

    @Test
    @DisplayName("测试加法")
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
     * 失败测试
     */
    @Test
    @DisplayName("测试加法失败")
    public void testEqualsAndNotEqualsFail() {
        // set up
        DemoUtils demoUtils = new DemoUtils();
        int expected = 6;
        // execute
        int actual = demoUtils.intAdd(2, 3);
        // assert
        Assertions.assertEquals(expected, actual, "2+4 must be 6");
    }

    /**
     * 检查对你是否为空
     */
    @Test
    @DisplayName("测试对象为空")
    public void testAssertNull() {
        // set up
        DemoUtils demoUtils = new DemoUtils();
        String str1 = null;
        String str2 = "NOHI";

        // execute && assert
        Assertions.assertNull(demoUtils.checkNull(str1), "Object should be null");
        Assertions.assertNotNull(demoUtils.checkNull(str2), "Object should not be null");
    }

    @ParameterizedTest(name = "#{index} - Test with {0} and {1}")
    @MethodSource("argumentProvider")
    void test_method_multi(String str, int length) {
        log.info("str[{}], length[{}]", str, length);
    }
    static Stream<Arguments> argumentProvider() {
        return Stream.of(
                arguments("abc", 3),
                arguments("lemon", 2)
        );
    }

    @Test
    @DisplayName("List Array Equals")
    public void testArrayEquals() {
        List list1 = List.of("A", "B", "C");
        String[] stringArray = {"A", "B", "C"};

        DemoUtils demoUtils = new DemoUtils();
        Assertions.assertIterableEquals(list1, demoUtils.getList(), "list1 equals demoUtils.getList()");
        Assertions.assertArrayEquals(stringArray, demoUtils.getStrArray(), "stringArray equals demoUtils.getList()");
    }


    @Test
    @DisplayName("Throw Exception")
    public void testThrowException() {
        DemoUtils demoUtils = new DemoUtils();
        Assertions.assertThrowsExactly(Exception.class, () -> { demoUtils.throwException(-1); }, "-1 < 0 throw Exception");
        Assertions.assertDoesNotThrow(() -> { demoUtils.throwException(1); }, "should not throw Exception");
    }

    @Test
    @DisplayName("Test Timeout")
    public void testTimeout() {
        Assertions.assertTimeout(Duration.ofSeconds(3), () -> { demoUtils.sleep(2); }, "必须3s内执行完成1");
        Assertions.assertTimeout(Duration.ofSeconds(3), () -> { demoUtils.sleep(4); }, "必须3s内执行完成2");
    }
}
