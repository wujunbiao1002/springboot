package com.wjb.springboot.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <b><code>WebSocketServer</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/12/4 12:51.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since java 0.0.1
 */
@ServerEndpoint(value = "/ws/asset")
@Component
@Slf4j
public class WebSocketServer {

    @PostConstruct
    public void init() {
        log.info("WebSocket 加载");
    }

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
     */
    private static final CopyOnWriteArraySet<Session> SESSION_SET = new CopyOnWriteArraySet<>();

    /**
     * 连接建立成功调用的方法
     * @param session 会话
     */
    @OnOpen
    public void onOpen(Session session) {
        SESSION_SET.add(session);
        // 在线数加1
        int cnt = ONLINE_COUNT.incrementAndGet();
        log.info("有连接加入，当前连接数为：{}", cnt);
        log.info("Session，当前连接ID：{}", session.getId());
        sendMessage(session, "连接成功");
    }

    /**
     * 连接关闭调用的方法
     * @param session 会话
     */
    @OnClose
    public void onClose(Session session) {
        SESSION_SET.remove(session);
        int cnt = ONLINE_COUNT.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     *
     * @param message 客户端发送过来的消息
     * @param session 客户端会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：{}", message);
        sendMessage(session, "收到消息，消息内容：" + message);
    }

    /**
     * 出现错误
     *
     * @param session 客户端会话
     * @param error 异常消息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}", error.getMessage(), session.getId());
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     *
     * @param session 客户端会话
     * @param message 消息体
     */
    public static void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 指定Session发送消息
     *
     * @param sessionId 会话id
     * @param message 发送消息体
     */
    public static void sendMessage(String sessionId, String message) {
        Session session = null;
        for (Session s : SESSION_SET) {
            if (s.getId().equals(sessionId)) {
                session = s;
                break;
            }
        }
        if (session != null) {
            sendMessage(session, message);
        } else {
            log.warn("没有找到你指定ID的会话：{}", sessionId);
        }
    }

    /**
     * 群发消息
     *
     * @param message 群发给所有监听客户端
     */
    public static void broadCastInfo(String message)  {
        for (Session session : SESSION_SET) {
            if (session.isOpen()) {
                sendMessage(session, message);
            }
        }
    }
}
