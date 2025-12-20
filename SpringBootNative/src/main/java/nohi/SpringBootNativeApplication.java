package nohi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
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
@ServletComponentScan
public class SpringBootNativeApplication {
    private static final Logger log = LoggerFactory.getLogger(SpringBootNativeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNativeApplication.class, args);
    }

    @GetMapping(path = "/main")
    public ResponseEntity<String> hello() {
        log.info("info");
        log.debug("debug");
        log.warn("warn");
        return ResponseEntity.ok("Hello world! SpringBootNative");
    }
}
