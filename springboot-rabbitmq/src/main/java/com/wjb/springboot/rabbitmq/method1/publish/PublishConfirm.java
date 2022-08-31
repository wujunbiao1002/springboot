package com.wjb.springboot.rabbitmq.method1.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.wjb.springboot.rabbitmq.method1.util.RabbitMqUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * <b><code>Work1</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/29 15:47.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
public class PublishConfirm {
    public static void main(String[] args) {
        //单个发送确认机制 发布耗时839ms:
//        publishMessageIndividually();
        // 批量发送确认机制 发布耗时136ms:
//        publishMessageBatch();
        // 异步确认发送机制  发布耗时37ms:
        publishMessageAsync();
    }

    @SneakyThrows
    public static void publishMessageIndividually() {
        Channel channel = RabbitMqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);
        // 开启发布确认
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for (int i = 1; i <= 1000; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            boolean b = channel.waitForConfirms();
            if (b) {
                log.info("{}:消息发送成功", message);
            }
        }
        long end = System.currentTimeMillis();
        log.info("发布耗时{}:", (end - begin) + "ms");
    }

    @SneakyThrows
    public static void publishMessageBatch() {
        Channel channel = RabbitMqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);
        // 开启发布确认
        channel.confirmSelect();
        int batchSize = 10;
        int outstandingMessageCount = 0;
        long begin = System.currentTimeMillis();
        for (int i = 1; i <= 1000; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            outstandingMessageCount++;
            if (batchSize == outstandingMessageCount) {
                channel.waitForConfirms();
                outstandingMessageCount = 0;
            }
        }
        if (outstandingMessageCount > 0) {
            channel.waitForConfirms();
        }
        long end = System.currentTimeMillis();
        log.info("发布耗时{}:", (end - begin) + "ms");
    }

    @SneakyThrows
    public static void publishMessageAsync() {
        Channel channel = RabbitMqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);
        // 开启发布确认
        channel.confirmSelect();
        ConcurrentSkipListMap<Long, Object> outstandingConfirms = new ConcurrentSkipListMap<>();

        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
            if (multiple) {
                ConcurrentNavigableMap<Long, Object> headMap = outstandingConfirms.headMap(deliveryTag, true);
                log.info("发布确认的序号{}", headMap);
                headMap.clear();
            } else {
                outstandingConfirms.remove(deliveryTag);
            }
        };
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            String message = (String) outstandingConfirms.get(deliveryTag);
            log.info("发布的消息{},未被确认，序号为{}", message, deliveryTag);
        };
        channel.addConfirmListener(ackCallback, nackCallback);

        long begin = System.currentTimeMillis();
        for (int i = 1; i <= 1000; i++) {
            String message = i + "";
            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
            channel.basicPublish("", queueName, null, message.getBytes());
        }

        long end = System.currentTimeMillis();
        log.info("发布耗时{}:", (end - begin) + "ms");
    }
}
