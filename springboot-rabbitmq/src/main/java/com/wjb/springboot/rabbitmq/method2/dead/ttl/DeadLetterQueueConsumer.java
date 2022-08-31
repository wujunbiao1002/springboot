package com.wjb.springboot.rabbitmq.method2.dead.ttl;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <b><code>DeadLetterQueueConsumer</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/29 23:37.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @SneakyThrows
    @RabbitListener(queues = TtlQueueConfig.DEAD_LETTER_QUEUE)
    public void receiveD(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到死信队列信息{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
