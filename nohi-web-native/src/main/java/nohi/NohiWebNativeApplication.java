package nohi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * nohi-web 启动程序
 * @author NOHI
 * @date 2022/9/14 15:05
 */
@ServletComponentScan
@EnableFeignClients
@SpringBootApplication
public class NohiWebNativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NohiWebNativeApplication.class, args);
    }
}
