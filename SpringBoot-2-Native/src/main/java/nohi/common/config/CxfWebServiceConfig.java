package nohi.common.config;//package nohi.cxf.configue;
//
//import javax.xml.ws.Endpoint;
//
//import nohi.cxf.service.hello.HelloWebService;
//import nohi.cxf.service.qunyao.GiantHopeBankCenterSoap;
//import org.apache.cxf.Bus;
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.apache.cxf.transport.servlet.CXFServlet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
///**
// * @author NOHI
// * @program: springboot-webservice
// * @description:
// * @create 2020-04-08 09:04
// **/
//@Configuration
//public class CxfWebServiceConfig {
//    @Autowired
//    private Bus bus;
//    @Autowired
//    private HelloWebService helloWebService;
//
//    @Autowired
//    private GiantHopeBankCenterSoap hopeBankCenterSoap;
//
//    @Bean("cxfServletRegistration")
//    public ServletRegistrationBean dispatcherServlet() {
//        //注册servlet 拦截/ws 开头的请求 不设置 默认为：/services/*
//        return new ServletRegistrationBean(new CXFServlet(), "/webservice/*");
//    }
//
//    /*
//     * 发布endpoint
//     */
//    @Bean
//    public Endpoint endpoint() {
//        EndpointImpl endpoint = new EndpointImpl(bus, helloWebService);
//        endpoint.publish("/helloWebService");//发布地址
//        return endpoint;
//    }
//
//    @Bean
//    public Endpoint endpoint2() {
//        EndpointImpl endpoint = new EndpointImpl(bus, hopeBankCenterSoap);
//        endpoint.publish("/hopeBankCenterSoap");//发布地址
//        return endpoint;
//    }
//}
