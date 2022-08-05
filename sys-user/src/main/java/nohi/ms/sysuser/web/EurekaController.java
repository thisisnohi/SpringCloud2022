package nohi.ms.sysuser.web;

import com.netflix.discovery.DiscoveryManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import nohi.ms.sysuser.feign.UserFeiginApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    private RestTemplate restTemplate;

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
        log.debug("list[{}]", null == list ? "null" : list.size());
        log.debug("====userLists end======");
        return list;
    }

    @GetMapping(value = "/rest/user/lists")
    public List<UserDTO> restUserLists() {
        log.debug("====userLists start======");
        String serviceUrl = "http://sys-user";
        String urlPath = serviceUrl + "/users/lists";

        log.debug("urlPath:{}", urlPath);
        // ResponseEntity<List> res1 =  restTemplate.getForEntity(urlPath, List.class);
        String resp1 = restTemplate.getForObject(urlPath, String.class);
        log.debug("response:{}",  resp1);
        log.info("================");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<List> response = restTemplate.exchange(urlPath, HttpMethod.GET, new HttpEntity<>(headers), List.class);
        log.debug("response:{}", null == response ? "null" : response.getBody());
        if (response == null) {
            return null;
        }
        return response.getBody();
    }
}
