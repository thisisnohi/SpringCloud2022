package nohi.demo.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <h3>SpringCloud2022</h3>
 * 可以被3整除，打印Fizz
 * 可以被5整除，打印Buzz
 * 可以被3和5同时整除，打印FizzBuzz
 * 其他打印相应数据
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/05/17 12:25
 **/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {

    @Test
    @DisplayName("被三整除")
    @Order(1)
    void testDivisibleByThree() {
        String expect = "Fizz";
        assertEquals(expect, FizzBuzz.compute(3), "3可被三整除，返回Fizz");
    }

    @Test
    @DisplayName("被5整除")
    @Order(2)
    void testDivisibleByFivee() {
        String expect = "Buzz";
        assertEquals(expect, FizzBuzz.compute(5), "3可被三整除，返回Fizz");
    }

    @Test
    @DisplayName("被3和5整除")
    @Order(3)
    void testDivisibleByThreeANDFive() {
        String expect = "FizzBuzz";
        assertEquals(expect, FizzBuzz.compute(15), "可被3和5整除，返回FizzBuzz");
    }

    @Test
    @DisplayName("无法被3和5整除")
    @Order(4)
    void testNotDivisibleByThreeANDFive() {
        String expect = "1";
        assertEquals(expect, FizzBuzz.compute(1), "无法被3和5整除");
    }

    @DisplayName("CsvSource")
    @ParameterizedTest(name = "value={0},expect={1}")
    @CsvSource({"1, 1", "2, 2", "3, Fizz", "4, 4", "5, Buzz", "6, Fizz", "15, FizzBuzz"})
    @Order(5)
    void testWithCsvSource(int value, String expect) {
        assertEquals(expect, FizzBuzz.compute(value));
    }

    @DisplayName("CsvFileSource")
    @ParameterizedTest(name = "value={0},expect={1}")
    @CsvFileSource(resources = "/small-test-data.csv")
    @Order(6)
    void testWithCsvFileSource(int value, String expect) {
        assertEquals(expect, FizzBuzz.compute(value));
    }
}
