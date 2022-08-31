package com.wjb.springboot.rabbitmq.method1.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.wjb.springboot.rabbitmq.method1.util.RabbitMqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * <b><code>eceiveLog</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/29 19:58.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
public class DirectReceiveLog02 {
    private static final String EXCHANGE_NAME = "X";
    private static final String QUEUE_NAME = "disk";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning");
        log.info("info、warning等待接收消息……");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody());
            msg = "接收绑定键：" + message.getEnvelope().getRoutingKey() + "，消息：" + msg;
            log.info("{}",msg);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
