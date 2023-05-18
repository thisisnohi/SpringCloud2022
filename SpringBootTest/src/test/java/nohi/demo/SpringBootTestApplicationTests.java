package nohi.demo;

import nohi.boot.models.CollegeStudent;
import nohi.boot.models.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 不同包下的测试
 *  @SpringBootTest(classes = SpringBootTestApplication.class)
 */
@SpringBootTest
@DisplayName("SrpingBoot单元测试,不同包下运行测试")
class SpringBootTestApplicationTests {
	@Autowired
	CollegeStudent collegeStudent;

	@Autowired
	Student student;

	@Test
	@DisplayName("测试Bean获取，根本不运行")
	@Order(1)
	void testGetBean(){
		Assertions.assertNotNull(collegeStudent, "CollegeStudent 不应为空");
		Assertions.assertNotNull(student, "CollegeStudent 不应为空");
	}

}
