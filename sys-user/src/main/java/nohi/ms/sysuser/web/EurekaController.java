package nohi.ms.sysuser.web;

import com.netflix.discovery.DiscoveryManager;
import feign.Param;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nohi.ms.sys.user.dto.userquery.UserDTO;
import nohi.ms.sysuser.feign.UserFeignApi;
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
@SuppressWarnings("deprecation")
@RestController
@RequestMapping("/eureka")
@Tag(name = "Eureka配置获取")
@Slf4j
public class EurekaController {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserFeignApi userFeignApi;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    @Operation(summary = "获取应用列表", description = "获取应用列表")
    @GetMapping(value = "/apps")
    public Object apps() {
        log.debug("====apps======");
        return discoveryClient.getServices();
    }

    @Operation(summary = "获取应用地址", description = "获取应用地址清单")
    @GetMapping(value = "/apps/{id}")
    public Object instance(@PathVariable("id") String id) {
        log.debug("====instance====== {}", id);
        return discoveryClient.getInstances(id);
    }

    @Operation(summary = "应用下线", description = "应用下线")
    @GetMapping(value = "/offline")
    public void offline() {
        log.debug("====offline======");
        DiscoveryManager.getInstance().shutdownComponent();
    }

    @Operation(summary = "通过feign获取用户列表", description = "feign获取用户列表")
    @GetMapping(value = "/feign/user/lists")
    public List<UserDTO> userLists() {
        log.debug("====userLists start======");
        List<UserDTO> list = userFeignApi.lists();
        log.debug("list[{}]", null == list ? "null" : list.size());
        log.debug("====userLists end======");
        return list;
    }

    @Operation(summary = "通过feign获取用户列表, sleep指定秒", description = "feign获取用户列表")
    @GetMapping(value = "/feign/user/lists-sleep")
    public List<UserDTO> userListsSleep(@Param("sleep") int sleep) {
        log.debug("====userLists start======sleep[{}]", sleep);
        List<UserDTO> list = userFeignApi.listsSleep(sleep);
        log.debug("list[{}]", null == list ? "null" : list.size());
        log.debug("====userLists end======");
        return list;
    }

    @Operation(summary = "通过RestTemplate获取用户列表", description = "获取用户列表：RestTemplate ")
    @GetMapping(value = "/rest/user/lists")
    public List<UserDTO> restUserLists() {
        log.debug("====userLists start======");
        String serviceUrl = "http://SYS-USER";
        String urlPath = serviceUrl + "/user/lists";

        log.debug("urlPath:{}", urlPath);
        String resp1 = restTemplate.getForObject(urlPath, String.class);
        log.debug("response:{}", resp1);
        log.info("================");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<List> response = restTemplate.exchange(urlPath, HttpMethod.GET, new HttpEntity<>(headers), List.class);
        log.debug("response:{}", response.getBody());
        return response.getBody();
    }

}
