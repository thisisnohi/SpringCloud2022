package nohi.ms.sysuser.web;

import com.alibaba.csp.sentinel.AsyncEntry;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.common.sentinel.ClientFallBack;
import nohi.ms.common.sentinel.HanlderExpection;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import nohi.ms.sysuser.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 *
 * @author NOHI
 * 2022-07-11 13:46
 **/
@Tag(name = "Sentinel")
@RestController
@RequestMapping("/sentinel")
@Slf4j
public class SentinelResourceController {
    public static final String RESOURCE_LISTS = "/sentinel/lists";
    public static final String RESOURCE_LISTS_SLEEP = "/sentinel/lists-sleep";

    private int demoCount = 10;

    @Autowired
    private SentinelService sentinelService;

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

        // sentinelService
        rule = new FlowRule();
        // 设置资源名称
        rule.setResource(SentinelService.RESOURCE_LISTS);
        // 指定限流模式为QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 指定QPS限流阈值
        rule.setCount(5);
        ruleList.add(rule);

        rule = new FlowRule();
        // 设置资源名称
        rule.setResource(SentinelService.RESOURCE_LISTS_SLEEP);
        // 指定限流模式为QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 指定QPS限流阈值
        rule.setCount(2);
        ruleList.add(rule);

        rule = new FlowRule();
        // 设置资源名称
        rule.setResource(SentinelService.RESOURCE_SYNC_METHOD);
        // 指定限流模式为QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 指定QPS限流阈值
        rule.setCount(2);
        ruleList.add(rule);

        // 加载该规则
        FlowRuleManager.loadRules(ruleList);
    }

    @Operation(summary = "lists返回列表", description = "返回列表")
    @GetMapping(value = "/lists")
    @SentinelResource(value = RESOURCE_LISTS,
            // 限流
            blockHandlerClass = HanlderExpection.class, blockHandler = "AllHandlerExpection",
            // 失败
            fallbackClass = ClientFallBack.class, fallback = "fallBack")
    public List<UserDTO> lists() {
        log.info("sentinel.lists...");
        List<UserDTO> list = Lists.newArrayList();
        for (int i = 0; i < demoCount; i++) {
            UserDTO dto = UserDTO.builder().userId("U_" + i).userNo("" + i).userName("NAME_" + i).address(LocalDateTime.now().toString()).build();
            list.add(dto);
        }
        return list;
    }

    @Operation(summary = "lists-sleep", description = "用户列表,服务sleep指定秒")
    @GetMapping(value = "/lists-sleep")
    @SentinelResource(RESOURCE_LISTS_SLEEP)
    public List<UserDTO> listsWithSleep(Integer sleep) throws InterruptedException {
        log.info("listsWithSleep...sleep[{}]", sleep);
        if (null != sleep && sleep > 0) {
            TimeUnit.SECONDS.sleep(sleep);
        } else {
            sleep = 0;
        }
        List<UserDTO> list = Lists.newArrayList();
        for (int i = 0; i < sleep; i++) {
            UserDTO dto = UserDTO.builder().userId("U_" + i).userNo("" + i).userName("NAME_" + i).build();
            list.add(dto);
        }
        log.info("users.lists...return data size[{}]", list.size());
        return list;
    }

    @Operation(summary = "service方法熔断", description = "service方法熔断")
    @GetMapping(value = "/service")
    public List<UserDTO> serviceSentinel(String type, Integer runTimes) {
        log.info("sentinel.serviceSentinel...type[{}] runTimes[{}]", type, runTimes);
        List<UserDTO> list = Lists.newArrayList();
        for (Integer i = 0; i < runTimes; i++) {
            if ("1".equalsIgnoreCase(type)) {
                try (Entry entry = SphU.entry(SentinelService.RESOURCE_LISTS)) {
                    // 被保护的逻辑
                    sentinelService.lists();
                } catch (BlockException e) {
                    log.error("[{}] blocked {}", SentinelService.RESOURCE_LISTS, e.getCause());
                    // 处理被流控的逻辑
                    System.out.println("blocked!");
                }
            } else if ("2".equalsIgnoreCase(type)) {
                if (SphO.entry(SentinelService.RESOURCE_LISTS_SLEEP)) {
                    try {
                        // 正常的业务逻辑
                        list.addAll(sentinelService.listsWithSleep(0));
                    } finally {
                        SphO.exit();
                    }
                } else {
                    log.warn("[{}]当前请求被限流了！", SentinelService.RESOURCE_LISTS_SLEEP);
                    // 降级方案
                    return null;
                }
            }
        }
        return list;
    }

    @Operation(summary = "异步方法熔断降级", description = "异步方法熔断降级")
    @GetMapping(value = "/sync-method")
    public String syncMethod() {
        AsyncEntry asyncEntry = null;
        try {
            asyncEntry = SphU.asyncEntry(SentinelService.RESOURCE_SYNC_METHOD);
            // 正常的业务逻辑
            sentinelService.syncMethod();
        } catch (BlockException e) {
            log.info("当前请求被限流了：", e);
            // 降级方案
            return "系统繁忙，请稍后再试！";
        } finally {
            if (asyncEntry != null) {
                asyncEntry.exit();
            }
        }
        return "OK";
    }

}
