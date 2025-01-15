package nohi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author NOHI
 * 2021-06-03 17:41
 **/
@Component
@ConfigurationProperties(prefix = "biz-config")
@RefreshScope
public class BizConfig {

    private ZshConfig zsh;
    private NjConfig nj;
    private MockConfig mock;


    /**
     * mock配置
     *
     * @author NOHI
     * @date 2022/9/8 22:26
     */
    public static final class MockConfig {
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

    public static final class ZshConfig {
        private int port = 9001;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public static final class NjConfig {
        private int port = 9002;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public ZshConfig getZsh() {
        return zsh;
    }

    public void setZsh(ZshConfig zsh) {
        this.zsh = zsh;
    }

    public NjConfig getNj() {
        return nj;
    }

    public void setNj(NjConfig nj) {
        this.nj = nj;
    }

    public MockConfig getMock() {
        return mock;
    }

    public void setMock(MockConfig mock) {
        this.mock = mock;
    }
}
