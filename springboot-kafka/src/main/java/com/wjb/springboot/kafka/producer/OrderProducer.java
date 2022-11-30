package com.wjb.springboot.kafka.producer;

import com.alibaba.fastjson.JSON;
import com.wjb.springboot.kafka.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, String> template;

    @GetMapping("/send/{topic}/{id}")
    public String send(@PathVariable String topic, @PathVariable String id) {
        Order order = new Order(id + "-订单号", id + "-订单名称", id);
        try {
            template.send(topic, JSON.toJSONString(order)).addCallback(
                    success -> {
                        if (success == null) {
                            return;
                        }
                        // 消息发送到的topic
                        RecordMetadata recordMetadata = success.getRecordMetadata();
                        String getTopic = recordMetadata.topic();
                        // 消息发送到的分区
                        int partition = recordMetadata.partition();
                        // 消息在分区内的offset
                        long offset = recordMetadata.offset();
                        log.debug("实例变更信息推送成功: {}-{}-{}", getTopic, partition, offset);
                    },
                    exception -> log.error("实例变更信息推送失败 -> ", exception)
            );
        } catch (Exception e) {
            log.error("实例变更信息推送失败 -> ", e);
            return "失败";
        }
        return "成功";
    }
}
