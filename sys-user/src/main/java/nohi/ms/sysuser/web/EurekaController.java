package nohi.ms.sysuser.web;

import com.netflix.discovery.DiscoveryManager;
import feign.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RestController
@RequestMapping("/eureka")
@Slf4j
@Api(tags = "Eureka")
public class EurekaController {
    @Autowired
    private UserFeignApi userFeignApi;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "获取应用列表", notes = "获取应用列表清单")
    @GetMapping(value = "/apps")
    public Object apps() {
        log.debug("====apps======");
        return discoveryClient.getServices();
    }

    @ApiOperation(value = "获取应用地址", notes = "获取应用地址清单")
    @GetMapping(value = "/apps/{id}")
    public Object instance(@PathVariable("id") String id) {
        log.debug("====instance====== {}", id);
        return discoveryClient.getInstances(id);
    }

    @ApiOperation(value = "应用下线", notes = "应用下线")
    @GetMapping(value = "/offline")
    public void offline() {
        log.debug("====offline======");
        DiscoveryManager.getInstance().shutdownComponent();
    }
    @ApiOperation(value = "通过feign获取用户列表", notes = "feign获取用户列表")
    @GetMapping(value = "/feign/user/lists")
    public List<UserDTO> userLists() {
        log.debug("====userLists start======");
        List<UserDTO> list = userFeignApi.lists();
        log.debug("list[{}]", null == list ? "null" : list.size());
        log.debug("====userLists end======");
        return list;
    }
    @ApiOperation(value = "通过feign获取用户列表, sleep指定秒", notes = "feign获取用户列表")
    @GetMapping(value = "/feign/user/lists-sleep")
    public List<UserDTO> userListsSleep(@Param("sleep") int sleep) {
        log.debug("====userLists start======sleep[{}]", sleep);
        List<UserDTO> list = userFeignApi.listsSleep(sleep);
        log.debug("list[{}]", null == list ? "null" : list.size());
        log.debug("====userLists end======");
        return list;
    }

    @ApiOperation(value = "通过RestTemplate获取用户列表", notes = "获取用户列表：RestTemplate ")
    @GetMapping(value = "/rest/user/lists")
    public List<UserDTO> restUserLists() {
        log.debug("====userLists start======");
        String serviceUrl = "http://SYS-USER";
        String urlPath = serviceUrl + "/user/lists";

        log.debug("urlPath:{}", urlPath);
        String resp1 = restTemplate.getForObject(urlPath, String.class);
        log.debug("response:{}",  resp1);
        log.info("================");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<List> response = restTemplate.exchange(urlPath, HttpMethod.GET, new HttpEntity<>(headers), List.class);
        log.debug("response:{}", response.getBody());
        return response.getBody();
    }

}
