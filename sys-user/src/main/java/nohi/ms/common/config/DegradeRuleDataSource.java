//package nohi.ms.common.config;
//
//import com.alibaba.csp.sentinel.datasource.AbstractDataSource;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author NOHI
// * 2022-08-07 12:04
// **/
//@Slf4j
//@Component
//@ConditionalOnClass({AbstractDataSource.class, DegradeRuleManager.class})
//public class DegradeRuleDataSource extends AbstractDataSource<SentinelDegradeRuleConfig, List<DegradeRule>> implements ApplicationListener<RefreshScopeRefreshedEvent>, InitializingBean {
//
//    @Autowired
//    private SentinelDegradeRuleConfig degradeRuleProps;
//
//    public DegradeRuleDataSource() {
//        super(degradeRuleProps -> {
//            if (degradeRuleProps == null || !degradeRuleProps.isEnable() || CollectionUtils.isEmpty(degradeRuleProps.getRules())) {
//                return new ArrayList<>();
//            }
//            return degradeRuleProps.getRules().parallelStream().filter(SentinelDegradeRuleConfig::checkNotNull).collect(Collectors.toList());
//        });
//        log.info("DegradeRuleDataSource  degradeRuleProps[{}]", degradeRuleProps);
//    }
//
//    /**
//     * 实现AbstractDataSource方法，从数据源读取配置
//     */
//    @Override
//    public SentinelDegradeRuleConfig readSource() throws Exception {
//        log.info("readSource  degradeRuleProps[{}]", degradeRuleProps);
//        if (degradeRuleProps != null) {
//            return degradeRuleProps;
//        }
//        return null;
//    }
//
//    @Override
//    public void close() throws Exception {
//        log.info("close");
//    }
//
//    /**
//     * 初始化时调用一次，初始化加载熔断降级规则
//     *
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() {
//        log.info("afterPropertiesSet");
//        onApplicationEvent(new RefreshScopeRefreshedEvent());
//    }
//
//    /**
//     * 监听到RefreshScopeRefreshedEvent 事件时刷新熔断降级规则
//     *
//     * @param event the event to respond to
//     */
//    @Override
//    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
//        log.info("onApplicationEvent event[{}]", event);
//        try {
//            List<DegradeRule> rules = loadConfig();
//            if (!CollectionUtils.isEmpty(rules)) {
//                DegradeRuleManager.loadRules(rules);
//            }
//        } catch (Exception e) {
//            log.error("onApplicationEvent error:{}", e.getMessage());
//        }
//    }
//}
