package com.wjb.springboot.rabbitmq.method1.base;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.wjb.springboot.rabbitmq.method1.util.RabbitMqUtils;

import java.io.IOException;

/**
 * <b><code>Work01</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/28 20:04.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
public class Work01 {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("接收到消息：" + new String(message.getBody()));
            // 手动应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + "消费者取消接口接口回调逻辑");
        };
        System.out.println("C01消费者启动……");
        // 统一设置为1为不公平分发，谁处理快谁优先多处理
//        channel.basicQos(1);
        channel.basicQos(2);
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);
    }
}
