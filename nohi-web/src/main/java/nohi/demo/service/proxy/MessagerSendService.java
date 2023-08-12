package nohi.demo.service.proxy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author NOHI
 * @program: springboot-webservice
 * @description:
 * @create 2020-05-17 11:30
 **/
@Slf4j
@Service
public class MessagerSendService {

    @Value("${target.url}")
    private String targetUrl;

    public void msgSend(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("======MessagerSendService.msgSend=======");
        String contextPath = req.getContextPath();
        String queryString = req.getQueryString();
        String uri = req.getRequestURI();
        StringBuffer url = req.getRequestURL();
        log.info("contextPath:{}", contextPath);
        log.info("queryString:{}", queryString);
        log.info("uri:{}", uri);
        log.info("URL:{}", url);

        String contentType = null;
        Properties properties = new Properties();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = req.getHeader(name);
            log.info("head-name:[{}:{}]", name, value);
            if (!"content-length".equalsIgnoreCase(name)) {
                properties.put(name, value);
            }

            if ("content-type".equalsIgnoreCase(name)) {
                contentType = value;
            } else if ("target-url".equalsIgnoreCase(name)) {
                if (StringUtils.isNotBlank(value)) {

                }
            }
        }
        String msg = null;
        try {
            InputStream is = req.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            log.info("转发地址:{}", targetUrl + uri);
            log.info("请求报文:{}", sb.toString());
            // msg = HttpClientPoolUtils.post(targetUrl + uri, properties, sb.toString(), MediaType.APPLICATION_JSON_VALUE);
            log.info("响应报文：{}", msg);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            msg = e.getMessage();
        } finally {
            resp.getOutputStream().write(msg.getBytes());
        }
    }
}
