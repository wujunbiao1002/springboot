package com.wjb.springboot.rabbitmq.method1.dead.maxlength;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.wjb.springboot.rabbitmq.method1.util.RabbitMqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * <b><code>Consumer01</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/29 22:32.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
public class Consumer02 {
    private static final String DEAD_EXCHANGE_NAME = "dead_exchange";
    private static final String DEAD_QUEUE_NAME = "dead-queue";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(DEAD_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DEAD_QUEUE_NAME, false, false, false, null);
        channel.queueBind(DEAD_QUEUE_NAME, DEAD_EXCHANGE_NAME, "lisi");

        log.info("等待接收消息……");
        DeliverCallback deliverCallback = (tag, message) -> {
            log.info("Consumer02接收到死信消息{}", new String(message.getBody()));
        };
        channel.basicConsume(DEAD_QUEUE_NAME, true, deliverCallback,consumerTag->{});
    }
}
