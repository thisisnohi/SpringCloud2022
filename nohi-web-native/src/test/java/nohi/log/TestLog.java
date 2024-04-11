package nohi.log;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>日志</p>
 * @date 2024/04/11 10:28
 **/
@Slf4j
public class TestLog {


    @BeforeAll
    public static void logLevel() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        List<Logger> loggerList = loggerContext.getLoggerList();
        Map<String, String> resultMap = Maps.newHashMap();
        loggerList.stream().filter(item -> null != item.getLevel() && StringUtils.isNotBlank(item.getLevel().levelStr)).forEach(item -> {
            resultMap.put(item.getName(), item.getLevel().levelStr);
        });
        log.info("Level:{}", JSONObject.toJSONString(resultMap));
    }

    @Test
    public void log() {
        log.trace("This is a trace message");
        log.info("This is an info message");
        log.debug("This is a debug message");
        log.warn("This is a warn message");
        log.error("This is an error message");
    }

    /**
     * key + 分割符 + value，目前仅支持冒号(:)和等号(=)，示例如下：
     */
    @Test
    public void logMessg() {
        log.info("your email:{}, your phone:{}", "123456789@qq.com", "15310763497");
        log.info("your email={}, your cellphone={}", "123456789@qq.com", "15310763497");
        log.info("hi: {}", "this is nohi 15310763497");

    }

    @Test
    public void maskingPatternLayout() {
        Map<String, String> user = new HashMap<>();
        user.put("user_id", "123456");
        user.put("mobile", "18888888888");
        user.put("address", "朝阳区百子湾街道某小区1单元101");
        user.put("city", "北京市");
        user.put("country", "中国");
        user.put("email", "heiz123@163.com");
        log.info("customer info: {}", JSONObject.toJSONString(user));
    }

}
