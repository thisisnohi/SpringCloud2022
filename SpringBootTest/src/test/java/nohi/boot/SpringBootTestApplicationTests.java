package nohi.boot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootTestApplicationTests {
	@Test
	public void test() {
		int a = 1;
		Assertions.assertEquals(1, a);//判断二者是否相等
	}
	@Test
	void contextLoads() {
		int a = 1;
		Assertions.assertEquals(1, a);//判断二者是否相等
		System.out.println("=========");
	}

}
