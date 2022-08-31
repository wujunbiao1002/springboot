package com.wjb.springboot.rabbitmq.method1.dead.maxlength;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.wjb.springboot.rabbitmq.method1.util.RabbitMqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
public class Consumer01 {
    private static final String EXCHANGE_NAME = "normal_exchange";
    private static final String DEAD_EXCHANGE_NAME = "dead_exchange";
    private static final String QUEUE_NAME = "normal-queue";
    private static final String DEAD_QUEUE_NAME = "dead-queue";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DEAD_QUEUE_NAME, false, false, false, null);
        channel.queueBind(DEAD_QUEUE_NAME, DEAD_EXCHANGE_NAME, "lisi");

        // 正常队列绑定死信队列信息
        Map<String, Object> arg = new HashMap<>();
        arg.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
        arg.put("x-dead-letter-routing-key", "lisi");
        // 设置正常队列长度的限制
        arg.put("x-max-length", 6);

        channel.queueDeclare(QUEUE_NAME, false, false, false, arg);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "zhangsan");

        log.info("等待接收消息……");
        DeliverCallback deliverCallback = (tag, message) -> {
            log.info("Consumer01 接收到消息{}", new String(message.getBody()));
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback,consumerTag->{});
    }
}
