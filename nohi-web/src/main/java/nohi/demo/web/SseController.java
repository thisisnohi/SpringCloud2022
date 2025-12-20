package nohi.demo.web;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.service.websocket.WebSocketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>Server-Send Events 服务器发送事件，简称SSE</p>
 * @date 2025/12/20 01:13
 **/
@RestController
@RequestMapping("/sse")
@Tag(name = "SSE", description = "Server-Send Events 服务器发送事件，简称SSE")
@Slf4j
public class SseController {
    public final static CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        // 超时时间30s
        SseEmitter emitter = new SseEmitter(30_000L);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        WebSocketService.send2All("sse", "我上线了");
        return emitter;
    }
}
