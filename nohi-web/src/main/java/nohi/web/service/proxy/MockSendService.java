package nohi.web.service.proxy;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import nohi.common.config.BizConfig;
import nohi.web.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

/**
 * @author NOHI
 * @program: springboot-webservice
 * @description:
 * @create 2020-05-17 11:30
 **/
@Slf4j
@Service
public class MockSendService {
    private static final String DEFAULT_CHARSET = "UTF-8";

    @Autowired
    private BizConfig bizConfig;

    public <os> void msgSend(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(Locale.ROOT);
        log.info("{} ======MockSendService.msgSend=======", title);
        String contextPath = req.getContextPath();
        String queryString = req.getQueryString();
        String uri = req.getRequestURI();
        String charset = req.getParameter("charset");
        String sleepStr = req.getParameter("sleep");
        if (StringUtils.isBlank(charset)) {
            charset = DEFAULT_CHARSET;
        }
        StringBuffer URL = req.getRequestURL();
        log.info("{} contextPath:{}", title, contextPath);
        log.info("{} queryString:{}", title, queryString);
        log.info("{} uri:{}", title, uri);
        log.info("{} URL:{}", title, URL);
        log.info("{} charset:{}", title, charset);
        log.info("{} sleep:{}", title, sleepStr);
        log.info("{} getParameterMap:{}", title, null == req.getParameterMap() ? "IS NULL" : JSONObject.toJSONString(req.getParameterMap()));
        if (StringUtils.isNotBlank(sleepStr)) {
            BigDecimal sleep = new BigDecimal(sleepStr);
            try {
                Thread.sleep(sleep.longValue());
            } catch (Exception e) {
                log.warn("{} sleep异常:{}", title, e.getMessage());
            }
        }

        String contentType = null;
        Properties properties = new Properties();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = req.getHeader(name);
            log.info("{} head-name:[{}:{}]", title, name, value);
            if (!"content-length".equalsIgnoreCase(name)) {
                properties.put(name, value);
            }

            if ("content-type".equalsIgnoreCase(name)) {
                contentType = value;
            }
        }
        String msg = null;

        try (InputStream is = req.getInputStream(); BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            boolean flag = bizConfig.getMock().isEnable();
            String mockPath = bizConfig.getMock().getDirPath();
            log.debug("{} mock:{} mockPath:{}", title, flag, mockPath);


            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            log.debug("\n{} =========请求报文=====：{}", title, msg);
            if (flag) {
                String mockFile = mockPath + File.separator + uri + ".mock";
                msg = FileUtils.readStringfromPath(mockFile);
                log.debug("\n{} =========响应报文=====：{}", title, msg);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            msg = e.getMessage();
        } finally {
            resp.setCharacterEncoding(charset);
            resp.setHeader("Content-type", "text/html;charset=UTF-8");
            OutputStream os = resp.getOutputStream();
            os.write(msg.getBytes(charset));
            os.flush();
        }
    }
}
