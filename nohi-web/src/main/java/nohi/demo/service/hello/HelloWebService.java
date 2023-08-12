package nohi.demo.service.hello;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author NOHI
 * @program: springboot-webservice
 * @description:
 * @create 2020-04-08 09:06
 **/
@WebService()
public interface HelloWebService {
    @WebMethod
    String hello(@WebParam(name="name") String name);
}
