package nohi.common.config;//package nohi.cxf.configue;
//
//import io.swagger.annotations.ApiOperation;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * @author NOHI
// * 2021-06-03 16:46
// **/
//@Configuration
//@EnableSwagger2
//public class Swagger3Config {
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                .select()
////                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
////                .apis(RequestHandlerSelectors.basePackage("nohi.cxf.web")) // 设置扫描路径
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Swagger3接口文档")
//                .description("this is NOHI。")
//                .contact(new Contact("NOHI", "http://www.nohi.online", "thisisnohi@163.com"))
//                .version("1.0")
//                .build();
//    }
//}
