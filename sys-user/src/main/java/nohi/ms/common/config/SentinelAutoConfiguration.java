//package nohi.ms.common.config;
//
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
///**
// * @author NOHI
// * 2022-08-07 12:27
// **/
//@Configuration
//@ConditionalOnClass(DegradeRuleManager.class)
//@ConditionalOnProperty(value = "feign.sentinel.enable", havingValue = "true")
//@Import({DegradeRuleDataSource.class})
//@EnableConfigurationProperties({SentinelDegradeRuleConfig.class})
//public class SentinelAutoConfiguration {
//}
