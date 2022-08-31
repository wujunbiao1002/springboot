package com.wjb.springboot.rabbitmq.method1.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.wjb.springboot.rabbitmq.method1.util.RabbitMqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <b><code>DirectEmitLog</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/29 20:11.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
public class DirectEmitLog {
    private static final String EXCHANGE_NAME = "X";

    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        HashMap<String, String> map = new HashMap<>();
        map.put("info", "普通info信息");
        map.put("warning", "告警warning信息");
        map.put("error", "错误error信息");
        map.put("bug", "bug信息");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            channel.basicPublish(EXCHANGE_NAME, key, null, value.getBytes());
            log.info("发送消息{}", value);

        }

    }
}
