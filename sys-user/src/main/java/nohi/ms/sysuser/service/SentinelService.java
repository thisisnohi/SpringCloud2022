package nohi.ms.sysuser.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * serviceSentinel
 *
 * @author NOHI
 * 2022-08-07 14:40
 **/
@Service
@Slf4j
public class SentinelService {
    public static final String RESOURCE_LISTS = "/sentinel/service/lists";
    public static final String RESOURCE_LISTS_SLEEP = "/sentinel/service/lists-sleep";
    public static final String RESOURCE_SYNC_METHOD = "/sentinel/service/sync";

    @PostConstruct
    public void initRule() {
        List<FlowRule> ruleList = new ArrayList<>();
        FlowRule rule = new FlowRule();
        // 设置资源名称
        rule.setResource(RESOURCE_LISTS);
        // 指定限流模式为QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 指定QPS限流阈值
        rule.setCount(5);
        ruleList.add(rule);

        rule = new FlowRule();
        // 设置资源名称
        rule.setResource(RESOURCE_LISTS_SLEEP);
        // 指定限流模式为QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 指定QPS限流阈值
        rule.setCount(2);
        ruleList.add(rule);

        // 加载该规则
        FlowRuleManager.loadRules(ruleList);
    }

    @SentinelResource(RESOURCE_LISTS)
    public List<UserDTO> lists() {
        log.info("sentinel.lists...");
        List<UserDTO> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            UserDTO dto = UserDTO.builder().userId("U_" + i).userNo("" + i).userName("NAME_" + i).address(LocalDateTime.now().toString()).build();
            list.add(dto);
        }
        return list;
    }

    @SentinelResource(RESOURCE_LISTS_SLEEP)
    public List<UserDTO> listsWithSleep(Integer sleep) {
        log.info("listsWithSleep...sleep[{}]", sleep);
        if (null != sleep && sleep > 0) {
            try {
                TimeUnit.SECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            sleep = 0;
        }
        if (sleep == 1) {
            throw new RuntimeException("异常返回");
        }
        List<UserDTO> list = Lists.newArrayList();
        for (int i = 0; i < sleep; i++) {
            UserDTO dto = UserDTO.builder().userId("U_" + i).userNo("" + i).userName("NAME_" + i).build();
            list.add(dto);
        }
        log.info("users.lists...return data size[{}]", list.size());
        return list;
    }

    @SentinelResource(RESOURCE_SYNC_METHOD)
    @Async
    public void syncMethod() {
        log.info("sentinel.lists...");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
