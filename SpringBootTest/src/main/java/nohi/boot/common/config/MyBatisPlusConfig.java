package nohi.boot.common.config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>mybatis-plus</p>
 * @date 2023/05/28 22:07
 **/
@Slf4j
@Configuration
@MapperScan("nohi.boot.*.dao")
public class MyBatisPlusConfig {
    /**
     *	 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }



    /**
     *	 注入主键生成器
     */
    @Bean
    public IKeyGenerator keyGenerator(){
        return new H2KeyGenerator();
    }
}
