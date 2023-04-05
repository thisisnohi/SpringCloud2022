package nohi.ms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>WebSecurityConfig</p>
 * @date 2023/03/29 21:42
 **/
@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
//                .authorizeHttpRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .formLogin()
                ;
        return http.build();
    }
}
