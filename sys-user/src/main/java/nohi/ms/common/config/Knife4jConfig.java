//package nohi.ms.common.config;
//
//import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.env.Environment;
//import org.springframework.core.env.Profiles;
//import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
//
//import java.util.ArrayList;
//
///**
// *
// * @ConditionalOnExpression("${knife4j.enable}") 开启访问接口文档的权限  **knife4j.enable是在yml配置文件中配置为true**
// * @author NOHI
// * 2022-07-21 13:20
// **/
//@Configuration
//@EnableKnife4j
//@Import(BeanValidatorPluginsConfiguration.class)
//@ConditionalOnExpression("${knife4j.enable}")
//public class Knife4jConfig {
//    @Autowired
//    private Environment environment;
//
////
////    @Bean
////    public Docket docket() {
////        // 设置显示的swagger环境信息
////        Profiles profiles = Profiles.of("dev", "test");
////        // 判断是否处在自己设定的环境当中
////        boolean flag = environment.acceptsProfiles(profiles);
////
////        return new Docket(DocumentationType.SWAGGER_2)
////                .apiInfo(apiInfo())
////                // 配置api文档的分组
////                .groupName("SpringCloud")
////                // 配置是否开启swagger
////                .enable(flag)
////                .select()
////                // 配置扫描路径
////                .apis(RequestHandlerSelectors.basePackage("nohi.ms"))
////                // 配置过滤哪些
////                .paths(PathSelectors.any())
////                .build();
////    }
//
//    /**
//     * api基本信息
//     *
//     * @return
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfo("NOHI SpringCloud",
//                "测试swagger-ui",
//                "v1.0",
//                "http://nohi.online",
//                // 作者信息
//                new Contact("NOHI", "http://nohi.online", "thisisnohi@163.com"),
//                "Apache 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0",
//                new ArrayList());
//    }
//}
