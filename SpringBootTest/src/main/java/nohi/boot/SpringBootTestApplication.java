package nohi.boot;

import lombok.extern.slf4j.Slf4j;
import nohi.boot.models.CollegeStudent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * TEST
 * @author NOHI
 * @date 2023/1/12 13:47
 */
@Slf4j
@SpringBootApplication
public class SpringBootTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestApplication.class, args);
	}


	@Bean(name = "collegeStudent")
	@Scope(value = "prototype")
	CollegeStudent getCollegeStudent() {
		log.info("==>getCollegeStudent");
		return new CollegeStudent();
	}
}
