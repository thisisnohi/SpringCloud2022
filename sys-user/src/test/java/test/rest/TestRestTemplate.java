package test.rest;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author NOHI
 * 2022-07-19 18:12
 **/
public class TestRestTemplate {

    @Test
    public void test1(){
        String instanceId = "1";
        String appName = "SYS-USER";
        String appGroupName = "GROUP";

        String serviceUrl = "http://home.nohi.online:7001/eureka/";
        serviceUrl = "http://127.0.0.1:7001/eureka/";
        InstanceInfo info = new InstanceInfo(instanceId, appName, appGroupName, null,null,null, null,null,null, null,null,null, null,0,null, null,null,null, null,null,null, null,null,null, null,null);
        info.setLastUpdatedTimestamp();

        System.out.println(JSONObject.toJSONString(info));

        String urlPath = serviceUrl + "apps/" + info.getAppName();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(urlPath, HttpMethod.POST, new HttpEntity<>(info, headers), Void.class);



    }
}
