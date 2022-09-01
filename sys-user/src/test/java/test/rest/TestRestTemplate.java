package test.rest;

import com.alibaba.fastjson2.JSONObject;
import com.netflix.appinfo.InstanceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

    /**
    * 单独设置并使用RestTemplate
    * @author NOHI
    * @date 2022/8/25 21:51
    */
    @Test
    public void template(){
        RestTemplate restTemplate = new RestTemplate();
        String url="http://www.baidu.com";
        String id = "11";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("id",id);
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, header);
//        JSONObject response = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println(strbody.getBody());
    }
}
