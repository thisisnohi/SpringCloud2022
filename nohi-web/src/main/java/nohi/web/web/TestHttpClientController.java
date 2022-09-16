package nohi.web.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import nohi.common.config.BizConfig;
import nohi.socket.njserver.NjSocketService;
import nohi.socket.zshserver.SoketService;
import nohi.web.utils.HttpClientPoolUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-07-06 19:16
 **/
@Api(tags = "测试http client")
@RestController
@Slf4j
public class TestHttpClientController {

    @ApiOperation("testHttpClient")
    @GetMapping(value = {"testHttpClient"})
    public String testHttpClient() {
        CloseableHttpClient client = HttpClientPoolUtils.getHttpClient();
        log.debug("debug");
        log.info("INFO");
        log.warn("warn");
        log.error("error");
        String url = "http://www.baidu.com";
        String msg = HttpClientPoolUtils.doGetRequest(client, url);
        log.debug("msg:" + msg);
        return msg;
    }
}
