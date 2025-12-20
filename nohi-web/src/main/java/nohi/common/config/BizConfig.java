package nohi.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author NOHI
 * 2021-06-03 17:41
 **/
@Data
@Component
@ConfigurationProperties(prefix = "biz-config")
@RefreshScope
public class BizConfig {

    private ZshConfig zsh;
    private NjConfig nj;
    private MockConfig mock;


    /**
    * mock配置
    * @author NOHI
    * @date 2022/9/8 22:26
    */
    @Data
    public static final class MockConfig {
        // 是否开启
        private boolean enable = false;
        // mock文件目录
        private String dirPath;
    }

    @Data
    public static final class ZshConfig {
        private int port = 9001;
    }

    @Data
    public static final class NjConfig {
        private int port = 9002;
    }
}
