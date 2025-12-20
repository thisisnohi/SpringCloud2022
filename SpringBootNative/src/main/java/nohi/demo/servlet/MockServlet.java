package nohi.demo.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nohi.demo.common.utils.SpringContextHolder;
import nohi.demo.service.servlet.MockSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @program: springboot-webservice
 * @description:
 *    mockServlet
 *    @ServletComponentScan 与 @WebServlet配合使用
 * @author NOHI
 * @date 2025-07-27 10:09
 **/
@WebServlet(name = "mockServlet", urlPatterns = "/mock/*")
public class MockServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MockServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long start = System.currentTimeMillis();
        log.info("MockGET start /mock");
        MockSendService service = SpringContextHolder.getBean(MockSendService.class);
        service.msgSend(req, resp);
        log.info("MockGET over /mock 耗时:{}", System.currentTimeMillis() - start);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long start = System.currentTimeMillis();
        log.info("MockPOST start /mock");
        MockSendService service = SpringContextHolder.getBean(MockSendService.class);
        service.msgSend(req, resp);
        log.info("MockPOST over /mock 耗时:{}", System.currentTimeMillis() - start);
    }

}
