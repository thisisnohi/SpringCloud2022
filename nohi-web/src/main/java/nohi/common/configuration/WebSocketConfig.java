package nohi.common.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>WebSocketConfig</p>
 * @date 2025/12/20 00:32
 **/
@Configuration
public class WebSocketConfig {

    // 注入ServerEndpointExporter bean对象，自动注册使用注解@ServerEndpoint的bean
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
