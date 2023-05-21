//package nohi.boot.common.config;
//
//import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
//import com.google.common.collect.Lists;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.core.env.Profiles;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
//import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
//
//import java.lang.reflect.Field;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author NOHI
// * 2022-07-21 13:20
// * <p>@ConditionalOnExpression("${knife4j.enable}") 开启访问接口文档的权限</p>
// * **knife4j.enable是在yml配置文件中配置为true**
// **/
//@Configuration
//@EnableKnife4j
//@Slf4j
//public class Knife4jConfig {
//    @Autowired
//    private Environment environment;
//
//    @Bean
//    public Docket docket() {
//        // 设置显示的swagger环境信息
//        Profiles profiles = Profiles.of("dev", "test");
//        // 判断是否处在自己设定的环境当中
//        boolean flag = environment.acceptsProfiles(profiles);
//        log.info("[只有dev test环境启用swagger]docket flag:{}", flag);
//        if (!flag) {
//            log.info("==== 未开启 swagger ====");
//        }
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                // 配置api文档的分组
//                .groupName("NOHI-SpringBoot")
//                // 配置是否开启swagger
//                .enable(flag)
//                .select()
//                // 配置扫描路径
//                .apis(RequestHandlerSelectors.basePackage("nohi.boot"))
//                // 配置过滤哪些
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    /**
//     * api基本信息
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfo("NOHI SpringCloud",
//                "Spring Boot with Knife4j",
//                "v1.0",
//                "http://nohi.online",
//                // 作者信息
//                new Contact("NOHI", "http://nohi.online", "thisisnohi@163.com"),
//                "MIT License", "http://opensource.org/licenses/MIT",
//                Lists.newArrayList());
//    }
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
//
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
