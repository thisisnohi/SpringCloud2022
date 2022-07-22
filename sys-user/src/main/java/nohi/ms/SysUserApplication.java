package nohi.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户
 *
 * @author NOHI
 * 2022-07-11 13:45
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SysUserApplication {
    public static void main(String[] args) {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8887");

        SpringApplication.run(SysUserApplication.class, args);
    }
}
