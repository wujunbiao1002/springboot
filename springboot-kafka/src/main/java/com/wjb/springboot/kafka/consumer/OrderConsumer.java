package com.wjb.springboot.kafka.consumer;

import com.alibaba.fastjson.JSONObject;
import com.wjb.springboot.kafka.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {
    @KafkaListener(topics = "${kafka.topic1}")
    public void topic1Consumer(String record) {
        Order order = JSONObject.parseObject(record, Order.class);
        log.info("内容:{}", order.toString());
//        log.info("topic:{},partition:{}", record.topic(), record.partition());
    }

    @KafkaListener(topics = "${kafka.topic2}")
    public void topic2Consumer(String record) {
        Order order = JSONObject.parseObject(record, Order.class);
        log.info("内容:{}", order.toString());
//        log.info("topic:{},partition:{},value:{}", record.topic(), record.partition(), record.value());
    }
}
