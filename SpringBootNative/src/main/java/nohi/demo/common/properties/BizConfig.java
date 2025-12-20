package nohi.demo.common.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <h3>SpringBootNative</h3>
 *
 * @author NOHI
 * @description <p>BizConfig</p>
 * @date 2025/07/27 10:58
 **/
@Component
@ConfigurationProperties(prefix = "biz-config")
public class BizConfig {
    private MockConfig mock;

    public MockConfig getMock() {
        return mock;
    }

    public void setMock(MockConfig mock) {
        this.mock = mock;
    }
}
