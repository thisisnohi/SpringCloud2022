package nohi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author NOHI
 * 2021-06-03 17:41
 **/
@Component
@ConfigurationProperties(prefix = "biz-config")
public class BizConfig {

}
