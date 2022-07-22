package nohi.ms.sysuser.web;

import com.netflix.discovery.DiscoveryManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import nohi.ms.sysuser.feign.UserFeiginApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Eureka
 *
 * @author NOHI
 * 2022-07-11 13:46
 **/
@RestController
@RequestMapping("/eureka")
@Slf4j
@Api(tags = "Eureka")
public class EurekaController {
    @Autowired
    private UserFeiginApi userFeiginApi;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/apps")
    public Object apps() {
        log.debug("====apps======");
        return discoveryClient.getServices();
    }

    @GetMapping(value = "/apps/{id}")
    public Object instance(@PathVariable("id") String id) {
        log.debug("====instance====== {}", id);
        return discoveryClient.getInstances(id);
    }

    @GetMapping(value = "/offline")
    public void offline() {
        log.debug("====offline======");
        DiscoveryManager.getInstance().shutdownComponent();
    }

    @GetMapping(value = "/feign/user/lists")
    public List<UserDTO> userLists() {
        log.debug("====userLists start======");
        List<UserDTO> list = userFeiginApi.lists();
        log.debug("list[{}]", null == list? "null" : list.size());
        log.debug("====userLists end======");
        return list;
    }
}
