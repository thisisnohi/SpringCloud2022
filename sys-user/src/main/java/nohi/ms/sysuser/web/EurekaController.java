package nohi.ms.sysuser.web;

import com.netflix.discovery.DiscoveryManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

/**
 * Eureka
 *
 * @author NOHI
 * 2022-07-11 13:46
 **/
@RestController
@RequestMapping("/eureka")
@Slf4j
public class EurekaController {

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
}
