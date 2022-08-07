//package nohi.ms.common.config;
//
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
//import lombok.Data;
//import lombok.ToString;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//
//import java.util.List;
//
///**
// * sentinel 服务降级配置
// *
// * @author NOHI
// * 2022-08-06 21:18
// **/
//@Data
//@ToString
//@RefreshScope
//@ConditionalOnClass(DegradeRule.class)
//@ConfigurationProperties(prefix = "feign.sentinel.degrade")
//public class SentinelDegradeRuleConfig {
//    /**
//     * 是否启用熔断降级功能
//     */
//    private boolean enable = true;
//    /**
//     * 熔断降级规则
//     */
//    private List<DegradeRule> rules;
//
//
//    public static boolean checkNotNull(DegradeRule rule) {
//        return rule != null && rule.getCount() > 0 && rule.getTimeWindow() > 0 && !StringUtils.isEmpty(rule.getResource());
//    }
//
//}
