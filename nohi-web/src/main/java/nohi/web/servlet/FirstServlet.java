package nohi.web.servlet;

import lombok.extern.slf4j.Slf4j;
import nohi.common.config.SpringContextUtils;
import nohi.web.service.proxy.MessagerSendService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author NOHI
 * @program: springboot-webservice
 * @description:
 * @create 2020-05-17 10:09
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
