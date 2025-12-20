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
public class MockConfig {
    // 是否开启
    private boolean enable = false;
    // mock文件目录
    private String dirPath;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}


