package com.wjb.springboot.rabbitmq.method1.exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.wjb.springboot.rabbitmq.method1.util.RabbitMqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * <b><code>TopicReceiveLog01</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/29 22:13.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
public class TopicReceiveLog01 {
    private static final String EXCHANGE_NAME = "topic_logs";
    private static final String QUEUE_NAME = "Q1";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*.orange.*");
        log.info("等待接收消息：……");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            log.info("接收队列:{}，绑定键：{}，消息{}", QUEUE_NAME, message.getEnvelope().getRoutingKey(), new String(message.getBody()));
        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, v->{});
    }
}
