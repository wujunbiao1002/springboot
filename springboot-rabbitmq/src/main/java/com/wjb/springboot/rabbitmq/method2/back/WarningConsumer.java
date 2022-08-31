package com.wjb.springboot.rabbitmq.method2.back;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <b><code>WarningConsumer</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/31 13:28.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
@Component
public class WarningConsumer {
    @RabbitListener(queues = BackConfig.WARNING_QUEUE_NAME)
    public void receiveWaringMsg(Message message) {
        log.warn("告警发现不可路由信息:{}", new String(message.getBody()));
    }
}
