package nohi.demo.service.websocket;


import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.web.SseController;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>WebSocketService</p>
 * @date 2025/12/20 00:35
 **/
@Slf4j
@Component
@ServerEndpoint("/websocket/{terminalId}")
public class WebSocketService {
    /**
     * 保存连接信息
     */
    private static final Map<String, Session> CLIENTS = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("terminalId") String terminalId, Session session) throws Exception {
        log.info(session.getRequestURI().getPath() + "，打开连接开始：" + session.getId());
        // 当前连接已存在，关闭
        if (CLIENTS.containsKey(terminalId)) {
            onClose(CLIENTS.get(terminalId));
        }
        CLIENTS.put(terminalId, session);
        log.info(session.getRequestURI().getPath() + "，打开连接完成：" + session.getId());
    }

    @OnClose
    public void onClose(@PathParam("terminalId") String terminalId, Session session) throws Exception {
        log.info(session.getRequestURI().getPath() + "，关闭连接开始：" + session.getId());
        CLIENTS.remove(terminalId);
        log.info(session.getRequestURI().getPath() + "，关闭连接完成：" + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("前台发送消息：" + message);
        log.debug("共有[{}]客户端", CLIENTS.size());
        CLIENTS.forEach((terminalId, client) -> {
            sendMessage(terminalId, terminalId + ": " + message);
        });
        log.info("向sse发送消息");
        SseController.emitters.forEach(emitter -> {
            try {
                emitter.send(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error.toString());
    }

    public void onClose(Session session) {
        //判断当前连接是否在线
        //        if (!session.isOpen()) {
        //            return;
        //        }
        try {
            session.close();
        } catch (IOException e) {
            log.error("关闭连接异常：" + e);
        }
    }

    public void sendMessage(String message, Session session) {
        try {
            session.getAsyncRemote().sendText(message);
            log.info("推送成功：{}", message);
        } catch (Exception e) {
            log.error("推送异常：{}", e.getMessage(), e);
        }
    }

    public static boolean sendMessage(String terminalId, String message) {
        try {
            Session session = CLIENTS.get(terminalId);
            session.getAsyncRemote().sendText(message);
            log.info("推送成功：{}", message);
            return true;
        } catch (Exception e) {
            log.error("推送异常：{}", e.getMessage(), e);
            return false;
        }
    }

    public static void send2All(String from, String message) {
        try {
            log.debug("{} 向共有[{}]客户端，发送消息", from, CLIENTS.size());
            CLIENTS.forEach((terminalId, client) -> {
                sendMessage(terminalId, from + ": " + message);
            });
        } catch (Exception e) {
            log.error("推送异常：{}", e.getMessage(), e);
        }
    }
}
