package com.wjb.springboot.websocket.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * <b><code>WebSocketConfig</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/12/4 12:51.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since java 0.0.1
 */
@Configuration
@Slf4j
public class WebSocketConfig {
    /**
     * 给spring容器注入这个ServerEndpointExporter对象
     * 相当于xml：
     * <beans>
     * <bean id="serverEndpointExporter" class="org.springframework.web.socket.server.standard.ServerEndpointExporter"/>
     * </beans>
     * <p>
     * 检测所有带有@serverEndpoint注解的bean并注册他们。
     *
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        log.info("我被注入了");
        return new ServerEndpointExporter();
    }
}
