package com.wjb.springboot.rabbitmq.method2.confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <b><code>ConfirmConsumer</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/30 18:49.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
@Component
public class ConfirmConsumer {
    @RabbitListener(queues = ConfirmConfig.QUEUE_NAME)
    public void receiveMsg(Message message){
        log.info("接收队列{}，消息{}",ConfirmConfig.QUEUE_NAME,new String(message.getBody()));
    }
}
