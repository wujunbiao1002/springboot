package com.wjb.springboot.websocket.controller;

import com.wjb.springboot.websocket.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <b><code>SockerTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/12/4 14:09.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since java 0.0.1
 */
@RestController
@Slf4j
@RequestMapping("/socket")
public class SocketController {

    @GetMapping("/push")
    public String pushData() {

        WebSocketServer.broadCastInfo("推送成功" + LocalDateTime.now());
        return "推送成功";
    }
}
