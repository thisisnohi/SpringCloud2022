package nohi.demo.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nohi.common.configuration.SpringContextUtils;
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
        long start = System.currentTimeMillis();
        log.info("MockGET start /mock");
        MockSendService service = SpringContextUtils.getBean(MockSendService.class);
        service.msgSend(req, resp);
        log.info("MockGET over /mock 耗时:{}", System.currentTimeMillis() - start);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long start = System.currentTimeMillis();
        log.info("MockPOST start /mock");
        MockSendService service = SpringContextUtils.getBean(MockSendService.class);
        service.msgSend(req, resp);
        log.info("MockPOST over /mock 耗时:{}", System.currentTimeMillis() - start);
    }

}
