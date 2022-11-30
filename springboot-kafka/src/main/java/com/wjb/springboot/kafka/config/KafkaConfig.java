package com.wjb.springboot.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Value("${kafka.topic1}")
    private String springbootTopic1;

    @Value("${kafka.topic2}")
    private String springbootTopic2;

    /*
     * 创建一个名为testtopic的Topic并设置分区数为3，分区副本数为2
     */
//    @Bean
//    public NewTopic initialTopic() {
//        return new NewTopic(springbootTopic1, 3, (short) 2);
//    }

    /*
     * 如果要修改分区数，只需修改配置值重启项目即可
     * 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
     */
//    @Bean
//    public NewTopic updateTopic() {
//        return new NewTopic(springbootTopic1, 10, (short) 2);
//    }
}
