package com.wjb.springboot.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ZabbixConsumer {

//    @KafkaListener(topics = "zabbix_data")
    public void zabbixConsumer(ConsumerRecord<String, String> record) {
        log.info("topic:{},partition:{},value:{}", record.topic(), record.partition(), record.value());
    }

//    @KafkaListener(topics = "zabbix_data")
    public void zabbixConsumer(List<ConsumerRecord<String, String>>  records) {
        log.info(">>>批量消费一次，records.size()={}", records.size());
        for(ConsumerRecord<String,String> record:records){
            log.info("topic:{},partition:{},value:{}", record.topic(), record.partition(), record.value());
        }
    }
}
