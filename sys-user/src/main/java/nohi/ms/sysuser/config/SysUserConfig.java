package nohi.ms.sysuser.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * sys-user配置
 *
 * @author NOHI
 * 2022-08-09 13:39
 **/
@Data
@ToString
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "sys-user")
public class SysUserConfig {
    private String userName;
    private int userAge;
    private String useraddress;
}
