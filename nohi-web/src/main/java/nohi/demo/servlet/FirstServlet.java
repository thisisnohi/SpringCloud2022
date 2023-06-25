package nohi.demo.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nohi.common.config.SpringContextUtils;
import nohi.demo.service.proxy.MessagerSendService;


import java.io.IOException;

/**
 * Servlet
 * springboot-webservice
 * @author NOHI
 * @date 2020-05-17 10:09
 **/
@WebServlet(name = "firstServlet", urlPatterns = "/proxy/*")  //标记为servlet，以便启动器扫描。
@Slf4j
public class FirstServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET /mock");
        resp.getWriter().append("firstServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET /mock");
        MessagerSendService service = SpringContextUtils.getBean(MessagerSendService.class);
        service.msgSend(req, resp);
    }

}
