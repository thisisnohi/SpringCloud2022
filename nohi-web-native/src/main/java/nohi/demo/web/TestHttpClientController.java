package nohi.demo.web;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.dto.httpclient.HttpClientReq;
import nohi.demo.dto.httpclient.HttpClientResp;
import nohi.demo.utils.HttpClientPoolUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-07-06 19:16
 **/
@Tag(name = "测试http client")
@RestController
@Slf4j
public class TestHttpClientController {

    @Value("${server.port}")
    private String port;

    @Operation(summary = "testHttpClient")
    @GetMapping(value = {"testHttpClient"})
    public String testHttpClient() {
        CloseableHttpClient client = HttpClientPoolUtils.getHttpClient();
        log.debug("debug");
        log.info("INFO");
        log.warn("warn");
        log.error("error");
        String url = "http://127.0.0.1:" + port + "/mock/http?sleep=100";
        String msg = HttpClientPoolUtils.doGetRequest(client, url);
        log.debug("msg:" + msg);
        return msg;
    }

    @Operation(summary = "postHttpClient")
    @PostMapping(value = {"postHttpClient"})
    public HttpClientResp postHttpClient(@RequestBody HttpClientReq req) {
        String title = req.getTraceId();
        long start = System.currentTimeMillis();
        HttpClientResp resp = null;

        log.info("{} 请求:{}", title, JSONObject.toJSONString(req));

        try {
            String url = "http://127.0.0.1:" + port + "/mock/http?sleep=100";
            String reqMsg = JSONObject.toJSONString(req);
            String msg = HttpClientPoolUtils.post(url, reqMsg);
            log.debug("msg:" + msg);
            resp = JSONObject.parseObject(msg, HttpClientResp.class);
        } catch (Exception e) {
            log.error("{} 异常:{}", title, e.getMessage());
            resp = new HttpClientResp();
            resp.setRetCode(HttpClientResp.RET_FAIL);
            resp.setRetMsg(e.getMessage());
        } finally {
            resp.setTraceId(req.getTraceId());
            resp.setTxCode(req.getTxCode());
            long end = System.currentTimeMillis();
            log.info("{} 耗时[{}]", title, end - start);
        }
        return resp;
    }
}
