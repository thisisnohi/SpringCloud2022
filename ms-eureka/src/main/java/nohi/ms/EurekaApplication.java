package nohi.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author NOHI
 * 2022-07-11 12:59
 **/
@SpringBootApplication
@EnableEurekaServer // 开启 Eureka server,接受其他微服务的注册
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
