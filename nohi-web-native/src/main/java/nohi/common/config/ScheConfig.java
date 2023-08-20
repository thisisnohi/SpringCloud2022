package nohi.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-12 18:49
 **/
@Configuration
public class ScheConfig {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    int i = 0;

    /**
     * 表示 在指定时间执行
     */
    @Scheduled(cron = "0 0/1 * ? * *")
    public void fixTimeExecution() {
//        System.out.println("在指定时间 " + sdf.format(new Date()) + "执行");
//        if (i++ % 5 == 3) {
//            System.out.println("运行时异常");
//            throw new RuntimeException("运行时异常");
//        } else if (i % 5 == 1) {
//            System.out.println("运行时异常");
//            throw new NullPointerException("1111");
//        }

    }
}
