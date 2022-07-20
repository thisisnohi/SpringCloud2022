package nohi.ms.common.config;

import nohi.ms.common.interceptor.log.LoggingRequestInterceptor;
import nohi.ms.common.utils.HttpRequestUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NOHI
 * 2022-07-19 17:27
 **/
@Configuration
public class RestTemplateConfig {

    /**
     * 初始化连接工厂
     * @return
     */
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        /**连接超时*/
        factory.setConnectTimeout(15000);
        /**读超时*/
        factory.setReadTimeout(5000);
        return factory;
    }


    /**
     * 初始化请求模板
     * @param simpleClientHttpRequestFactory
     * @return
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory simpleClientHttpRequestFactory,@Qualifier("loggingRequestInterceptor") LoggingRequestInterceptor loggingRequestInterceptor){

        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(loggingRequestInterceptor);
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

    /**
     * 返回请求工具类
     * @param restTemplate
     * @return
     */
    @Bean
    public HttpRequestUtils getHttpRequestUtils(RestTemplate restTemplate){
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils();
        httpRequestUtils.setRestTemplate(restTemplate);
        return httpRequestUtils;
    }
}
