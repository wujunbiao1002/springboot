package com.wjb.springboot.rabbitmq.method1.base;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.wjb.springboot.rabbitmq.method1.util.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;

/**
 * <b><code>Task01</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/28 20:11.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
public class Task01 {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println("启动完成");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("发送消息完成：" + message);
        }
    }
}
