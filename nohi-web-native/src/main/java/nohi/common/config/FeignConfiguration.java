package nohi.common.config;

import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:25
 **/
@Configuration
@Slf4j
public class FeignConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        System.out.println("System.out.println======feignLoggerLevel===========");
        //这里记录所有，根据实际情况选择合适的日志level
        return Logger.Level.FULL;
    }

    @Bean
    Logger getLogger(){
        return new Logger() {
            @Override
            protected void log(String configKey, String format, Object... args) {
                log.info(String.format(methodTag(configKey) + format, args));
            }
        };
    }

}
