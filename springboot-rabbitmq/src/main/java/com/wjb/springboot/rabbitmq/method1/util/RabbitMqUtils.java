package com.wjb.springboot.rabbitmq.method1.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;


/**
 * <b><code>RabbitMqUtils</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/28 18:21.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
public class RabbitMqUtils {
    @SneakyThrows
    public static Channel getChannel() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("12345678");
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}
