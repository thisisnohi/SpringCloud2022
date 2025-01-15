package nohi.demo.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nohi.common.config.SpringContextUtils;
import nohi.demo.service.proxy.MockSendService;

import java.io.IOException;

/**
 * @author NOHI
 * @program: springboot-webservice
 * @description:
 * @create 2020-05-17 10:09
 **/
@WebServlet(name = "mockServlet", urlPatterns = "/mock/*")
@Slf4j
public class MockServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET /mock");
        MockSendService service = SpringContextUtils.getBean(MockSendService.class);
        service.msgSend(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("POST /mock");
        MockSendService service = SpringContextUtils.getBean(MockSendService.class);
        service.msgSend(req, resp);
    }

}
