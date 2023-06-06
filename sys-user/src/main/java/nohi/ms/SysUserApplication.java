package nohi.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 用户
 *
 * @author NOHI
 * 2022-07-11 13:45
 **/
@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class SysUserApplication {
    public static void main(String[] args) {
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "8887");

        SpringApplication.run(SysUserApplication.class, args);
    }
}
