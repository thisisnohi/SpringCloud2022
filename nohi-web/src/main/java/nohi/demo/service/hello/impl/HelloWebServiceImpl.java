package nohi.demo.service.hello.impl;

/**
 * @author NOHI
 * @program: springboot-webservice
 * @description:
 * @create 2020-04-08 09:07
 **/

import nohi.demo.service.hello.HelloWebService;
import org.springframework.context.annotation.Configuration;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * TODO(描述这个类的作用)
 * @author zyl
 * @date 2019年3月27日
 */
@WebService(
        targetNamespace = "nohi.cxf", //wsdl命名空间
        serviceName = "HelloWebService",                 //portType名称 客户端生成代码时 为接口名称
        endpointInterface = "nohi.demo.service.hello.HelloWebService")  //指定发布webservcie的接口类，此类也需要接入@WebService注解
@Configuration
public class HelloWebServiceImpl implements HelloWebService {

    @Override
    public String Hello(@WebParam(name="name") String name) {
        System.out.println("欢迎你"+name);
        return "欢迎你"+name;
    }
}
