package com.wjb.springboot.rabbitmq.method1.dead.reject;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.wjb.springboot.rabbitmq.method1.util.RabbitMqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * <b><code>Producer</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/29 22:31.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
public class Producer {
    private static final String EXCHANGE_NAME = "normal_exchange";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        for (int i = 1; i <= 10; i++) {
            String msg = "info" + i;
            channel.basicPublish(EXCHANGE_NAME, "zhangsan", null, msg.getBytes());
            log.info("发送消息{}",msg);
        }
    }
}
