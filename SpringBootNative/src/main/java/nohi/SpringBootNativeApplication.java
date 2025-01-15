package nohi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringBootNative  启动程序
 * @author NOHI
 * @date 2023/6/18
 */
@RestController
@SpringBootApplication
public class SpringBootNativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNativeApplication.class, args);
    }

    @GetMapping(path = "/main")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello world! SpringBootNative");
    }
}
