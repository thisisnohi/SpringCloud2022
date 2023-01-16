package nohi.boot.demo.service;

import org.springframework.stereotype.Service;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/01/16 21:35
 **/
@Service
public class HelloService {

    public String sayHello(String some){
        return "Hello " + some;
    }
}
