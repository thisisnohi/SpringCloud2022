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
     * get请求
     * @param url 请求地址
     * @return 返回结果
     */
    public String sendGetRequest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 请求体，包括请求数据 body 和 请求头 headers
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return strbody.getBody();
    }

    /**
     * @param  url 请求地址
     * @param  data 数据
     * post请求 json
     */
    public String sendPostRequest(String url, JSONObject data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 请求体，包括请求数据 body 和 请求头 headers
        HttpEntity httpEntity = new HttpEntity(data, headers);
        ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return strbody.getBody();
    }

    /**
     * @param url 请求地址
     * @param data 数据
     * post请求 json
     */
    public String sendPostRequest(String url, String data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 请求体，包括请求数据 body 和 请求头 headers
        HttpEntity httpEntity = new HttpEntity(data, headers);
        ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return strbody.getBody();
    }
}
