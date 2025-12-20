package nohi.demo.service.servlet;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nohi.demo.common.properties.BizConfig;
import nohi.demo.common.utils.FileUtils;
import nohi.demo.common.utils.JSONObjectUtils;
import nohi.demo.servlet.MockServlet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

/**
 * <h3>SpringBootNative</h3>
 *
 * @author NOHI
 * @description <p>mockService</p>
 * @date 2025/07/27 10:41
 **/
@Service

public class MockSendService {
    private static final Logger log = LoggerFactory.getLogger(MockServlet.class);
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
        StringBuffer url = req.getRequestURL();
        log.info("{} contextPath:{}", title, contextPath);
        log.info("{} queryString:{}", title, queryString);
        log.info("{} uri:{}", title, uri);
        log.info("{} URL:{}", title, url);
        log.info("{} charset:{}", title, charset);
        log.info("{} sleep:{}", title, sleepStr);
        log.info("{} getParameterMap:{}", title, null == req.getParameterMap() ? "IS NULL" : JSONObjectUtils.toJsonString(req.getParameterMap()));
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
            boolean flag = true;
            String mockPath = bizConfig.getMock().getDirPath();
            log.debug("{} mock:{} mockPath:{}", title, flag, mockPath);


            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            log.debug("{} =========请求报文=====：{}", title, msg);
            if (flag) {
                String mockFile = mockPath + File.separator + uri + ".mock";
                msg = FileUtils.readStringfromPath(mockFile);
                log.debug("{} =========响应报文=====：{}", title, msg);
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

