//package nohi.ms.common.config;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
//import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author NOHI
// * 2022-07-21 14:20
// **/
//@Configuration
//@EnableOpenApi
////@EnableSwagger2
////@EnableWebMvc
//public class SwaggerConfig {
//    /**
//     * 用于读取配置文件 application.properties 中 swagger 属性是否开启2
//     */
//    @Value("${swagger.enabled}")
//    Boolean swaggerEnabled;
//
//        @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                // 是否开启swagger
//                .enable(swaggerEnabled)
//                .select()
//                // 过滤条件，扫描指定路径下的文件
//                .apis(RequestHandlerSelectors.basePackage("nohi.ms"))
//                // 指定路径处理，PathSelectors.any()代表不过滤任何路径
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        /*作者信息*/
//        Contact contact = new Contact("NOHI", "http://nohi.online", "thisisnohi@163.com");
//        return new ApiInfo(
//                "Spring Boot 集成 Swagger3",
//                "Spring Boot 集成 Swagger3 接口文档",
//                "v1.0",
//                "http://nohi.online",
//                contact,
//                "Apache 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0",
//                new ArrayList()
//        );
//    }
//    // 参考：SpringBoot 2.7.1整合Swagger3.0 https://blog.csdn.net/weixin_48687496/article/details/125847501
////    @Bean
////    public Docket api() {
////        // 自动生成文档：http://localhost:8080/v3/api-docs
////        // API接口文档界面：http://localhost:8080/swagger-ui/index.html
////        return new Docket(DocumentationType.SWAGGER_2)
////                .apiInfo(apiInfo())
////                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.inventec.dm.controller"))
////                .paths(PathSelectors.regex("/article/*.*|/api/v1/*.*|/*.*"))
////                .build();
////    }
////
////
////    private ApiInfo apiInfo() {
////        return new ApiInfo(
////                "AMS系统API",
////                "后台管理系统相关的接口",
////                "v1",
////                "协议地址",
////                new Contact("cy", "https://github.com/", "@163.com"),
////                "MIT License", "http://opensource.org/licenses/MIT",
////                Collections.emptyList());
////    }
//
//    /**
//     * 解决swagger在springboot2.7以后的空指针异常
//     */
//    @Bean
//    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
//
//        return new BeanPostProcessor() {
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
//                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
//                }
//                return bean;
//            }
//            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
//                List<T> copy = mappings.stream()
//                        .filter(mapping -> mapping.getPatternParser() == null)
//                        .collect(Collectors.toList());
//                mappings.clear();
//                mappings.addAll(copy);
//            }
//
//
//            @SuppressWarnings("unchecked")
//            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
//                try {
//                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
//                    field.setAccessible(true);
//                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
//                } catch (IllegalArgumentException | IllegalAccessException e) {
//                    throw new IllegalStateException(e);
//                }
//            }
//        };
//    }
//}
//package nohi.ms.common.config;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
//import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author NOHI
// * 2022-07-21 14:20
// **/
//@Configuration
//@EnableOpenApi
////@EnableSwagger2
////@EnableWebMvc
//public class SwaggerConfig {
//    /**
//     * 用于读取配置文件 application.properties 中 swagger 属性是否开启2
//     */
//    @Value("${swagger.enabled}")
//    Boolean swaggerEnabled;
//
//        @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                // 是否开启swagger
//                .enable(swaggerEnabled)
//                .select()
//                // 过滤条件，扫描指定路径下的文件
//                .apis(RequestHandlerSelectors.basePackage("nohi.ms"))
//                // 指定路径处理，PathSelectors.any()代表不过滤任何路径
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        /*作者信息*/
//        Contact contact = new Contact("NOHI", "http://nohi.online", "thisisnohi@163.com");
//        return new ApiInfo(
//                "Spring Boot 集成 Swagger3",
//                "Spring Boot 集成 Swagger3 接口文档",
//                "v1.0",
//                "http://nohi.online",
//                contact,
//               "MIT License", "http://opensource.org/licenses/MIT",
//                new ArrayList()
//        );
//    }
//    // 参考：SpringBoot 2.7.1整合Swagger3.0 https://blog.csdn.net/weixin_48687496/article/details/125847501
////    @Bean
////    public Docket api() {
////        // 自动生成文档：http://localhost:8080/v3/api-docs
////        // API接口文档界面：http://localhost:8080/swagger-ui/index.html
////        return new Docket(DocumentationType.SWAGGER_2)
////                .apiInfo(apiInfo())
////                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.inventec.dm.controller"))
////                .paths(PathSelectors.regex("/article/*.*|/api/v1/*.*|/*.*"))
////                .build();
////    }
////
////
////    private ApiInfo apiInfo() {
////        return new ApiInfo(
////                "AMS系统API",
////                "后台管理系统相关的接口",
////                "v1",
////                "协议地址",
////                new Contact("cy", "https://github.com/", "@163.com"),
////                "MIT License", "http://opensource.org/licenses/MIT",
////                Collections.emptyList());
////    }
//
//    /**
//     * 解决swagger在springboot2.7以后的空指针异常
//     */
//    @Bean
//    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
//
//        return new BeanPostProcessor() {
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
//                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
//                }
//                return bean;
//            }
//            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
//                List<T> copy = mappings.stream()
//                        .filter(mapping -> mapping.getPatternParser() == null)
//                        .collect(Collectors.toList());
//                mappings.clear();
//                mappings.addAll(copy);
//            }
//
//
//            @SuppressWarnings("unchecked")
//            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
//                try {
//                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
//                    field.setAccessible(true);
//                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
//                } catch (IllegalArgumentException | IllegalAccessException e) {
//                    throw new IllegalStateException(e);
//                }
//            }
//        };
//    }
//}
