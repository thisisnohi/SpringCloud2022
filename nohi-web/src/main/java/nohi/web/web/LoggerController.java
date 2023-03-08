package nohi.web.web;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 日志动态更新
 *
 * @author NOHI
 * 2022-07-25 12:11
 **/
@Slf4j
@Api(tags = "日志级别")
@RestController
@RequestMapping("/logger")
public class LoggerController {
    final String[] levels = {"ERROR", "WARN", "INFO", "DEBUG", "TRACE"};
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    public void loggers() {
        log.error("日志级别 ERROR");
        log.warn("日志级别 WARN");
        log.info("日志级别 INFO");
        log.debug("日志级别 DEBUG");
        log.trace("日志级别 TRACE");
    }

    public void loggers(Logger logger) {
        logger.error("日志级别 ERROR");
        logger.warn("日志级别 WARN");
        logger.info("日志级别 INFO");
        logger.debug("日志级别 DEBUG");
        logger.trace("日志级别 TRACE");
    }

    @ApiOperation(value = "获取日志级别列表", notes = "获取日志级别列表")
    @GetMapping("/level")
    public Map<String, String> level() {
        List<Logger> loggerList = loggerContext.getLoggerList();
        Map<String, String> resultMap = Maps.newHashMap();
        loggerList.stream().filter(item -> null != item.getLevel() && StringUtils.isNotBlank(item.getLevel().levelStr)).forEach(item -> {
            resultMap.put(item.getName(), item.getLevel().levelStr);
        });
        // 打印日志
        this.loggers();
        return resultMap;
    }

    @ApiOperation(value = "获取指定name对应日志级别", notes = "获取指定name对应日志级别")
    @GetMapping("/{package}/level")
    public Map<String, String> getLevel(@PathVariable("package") String packageName) {
        Map<String, String> resultMap = Maps.newHashMap();
        Logger logger = loggerContext.getLogger(packageName);
        if (null == logger) {
            log.warn("[{}]无对应Logger", packageName);
            resultMap.put("ERROR", "[" + packageName + "]无对应Logger");
            return resultMap;
        }
        resultMap.put(packageName, logger.getLevel().levelStr);
        log.info("[{}]日志级别[{}]", packageName, logger.getLevel().levelStr);
        this.loggers(logger);
        return resultMap;
    }

    @ApiOperation(value = "设置指定name对应日志级别", notes = "package:为指定名称/包路径 level: 日志级别")
    @GetMapping("/setLevel/{package}/{level}")
    public Map<String, String> setLevel(@PathVariable("package") String packageName, @PathVariable("level") String level) {
        log.info("set [{}]日志级别[{}]", packageName, level);
        Map<String, String> resultMap = Maps.newHashMap();
        if (StringUtils.isBlank(level)) {
            level = "INFO";
        }
        level = level.toUpperCase();
        if (!isAllowed(level)) {
            log.warn("[{}]不符合规范", level);
            resultMap.put("ERROR", "[" + level + "]不符合规范");
            return resultMap;
        }

        Logger logger = loggerContext.getLogger(packageName);
        if (null == logger) {
            log.warn("[{}]无对应Logger", packageName);
            resultMap.put("ERROR", "[" + packageName + "]无对应Logger");
            return resultMap;
        }
        switch (level) {
            case "TRACE":
                logger.setLevel(Level.TRACE);
                break;
            case "WARN":
                logger.setLevel(Level.WARN);
                break;
            case "ERROR":
                logger.setLevel(Level.ERROR);
                break;
            case "DEBUG":
                logger.setLevel(Level.DEBUG);
                break;
            case "INFO":
            default:
                logger.setLevel(Level.INFO);
                break;
        }
        this.loggers(logger);
        List<Logger> loggerList = loggerContext.getLoggerList();
        loggerList.stream().filter(item -> null != item.getLevel() && StringUtils.isNotBlank(item.getLevel().levelStr)).forEach(item -> {
            resultMap.put(item.getName(), item.getLevel().levelStr);
        });
        return resultMap;
    }

    /**
     * 判断是否是合法的日志级别
     *
     * @param level 日志等级
     * @return boolean
     */
    private boolean isAllowed(String level) {
        return Arrays.asList(levels).contains(level.toUpperCase());
    }
}
