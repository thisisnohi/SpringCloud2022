package nohi.boot;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import nohi.boot.models.CollegeStudent;
import nohi.boot.models.Student;
import nohi.boot.models.StudentGrades;
import org.junit.jupiter.api.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Array;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@DisplayName("SrpingBoot单元测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringBootTestApplicationTests {


    @Value("${info.appName}")
    private String appName;

    @Value("${info.appDesc}")
    private String appDesc;

    @Value("${info.groupId}")
    private String groupId;

    @Value("${info.artifactId}")
    private String artifactId;

    @Value("${info.version}")
    private String version;

    @Value("${info.school.name}")
    private String schoolName;

    @Autowired
    CollegeStudent collegeStudent;
    @Autowired
    StudentGrades studentGrades;

    @Autowired
    Student student;

    @Autowired
    ApplicationContext context;

    // 方法计数
    int count = 0;

    @BeforeAll
    static void beforeAll() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger("ch.qos.logback");
        if (null != logger) {
            logger.setLevel(Level.WARN);
        }
    }

    /**
     * 初始化
     */
    @BeforeEach
    void beforeEach() {
        count = count + 1;
        String title = "===Testing beforeEach " + "Testing: " + appName + " which is " + appDesc + "  Version: " + version + ". Execution of test method " + count;
        log.info("{}", title);

        collegeStudent.setFirstname("丁");
        collegeStudent.setLastname("NOHI");
        collegeStudent.setEmailAddress("thisisnohi@163.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
        collegeStudent.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("测试Bean获取")
    @Order(1)
    void testGetBean() {
        Assertions.assertNotNull(collegeStudent, "CollegeStudent 不应为空");
        Assertions.assertNotNull(student, "CollegeStudent 不应为空");
    }

    @Test
    @DisplayName("测试属性文件注入")
    @Order(2)
    void testPropertiesInjection() {
        log.info("appName:{}", appName);
        log.info("appDesc:{}", appDesc);
        log.info("groupId:{}", groupId);
        log.info("artifactId:{}", artifactId);
        log.info("version:{}", version);
    }

    @Test
    @DisplayName("测试成功添加")
    @Order(3)
    void testEqual() {
        Assertions.assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults()));
    }



    @DisplayName("验证原型对象实例化是否相同")
    @Test
    @Order(4)
    public void verifyStudentsArePrototypes() {
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);

        assertNotSame(student, studentTwo);
    }

    @DisplayName("平均分数  assertAll用法")
    @Test
    @Order(4)
    public void findGradePointAverage() {
        assertAll("Testing all assertEquals",
                ()-> assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
                        collegeStudent.getStudentGrades().getMathGradeResults())),
                ()-> assertEquals(88.31, studentGrades.findGradePointAverage(
                        collegeStudent.getStudentGrades().getMathGradeResults()))
        );
    }
}
