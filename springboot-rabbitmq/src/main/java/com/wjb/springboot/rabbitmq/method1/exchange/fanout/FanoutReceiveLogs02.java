package com.wjb.springboot.rabbitmq.method1.exchange.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.wjb.springboot.rabbitmq.method1.util.RabbitMqUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * <b><code>Fanout</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/29 17:45.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
public class FanoutReceiveLogs02 {

    private static final String EXCHANGE_NAME = "logs";

    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // 生成一个临时队列，队列名称随机
        String queueName = channel.queueDeclare().getQueue();
        // 把临时队列绑定到我们的exchange 其中routingkey为空字符串
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        log.info("02等待接受消息……");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            log.info("接受消息:{}", new String(message.getBody()));
        };
        CancelCallback cancelCallback = consumerTag -> {
        };
        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }

}
