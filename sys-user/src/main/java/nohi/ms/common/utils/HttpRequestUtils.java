package nohi.ms.common.utils;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author NOHI
 * 2022-07-19 17:28
 **/
public class HttpRequestUtils {
    private RestTemplate restTemplate;

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * @param : url
     * @description: post请求 get
     */
    public String sendGetRequest(String url) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(headers);//请求体，包括请求数据 body 和 请求头 headers
        ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return strbody.getBody();
    }

    /**
     * @param : url
     * @param : data
     * @description: post请求 json
     */
    public String sendPostRequest(String url, JSONObject data) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(data, headers);//请求体，包括请求数据 body 和 请求头 headers
        ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return strbody.getBody();
    }

    /**
     * @param : url
     * @param : data
     * @description: post请求 json
     */
    public String sendPostRequest(String url, String data) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(data, headers); // 请求体，包括请求数据 body 和 请求头 headers
        ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return strbody.getBody();
    }
}
