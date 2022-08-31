package com.wjb.springboot.rabbitmq.method2.confirm;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b><code>ConfirmConfig</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/30 18:31.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Configuration
public class ConfirmConfig {
    public static  final String QUEUE_NAME = "confirm_queue";
    public static  final String EXCHANGE_NAME = "confirm_exchange";

    public static  final String ROUTING_KEY = "key1";

    @Bean
    public Queue queueConfirm(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public DirectExchange exchangeConfirm(){
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).build();
    }

    @Bean
    public Binding queueConfirmBindingExchange(@Qualifier("queueConfirm") Queue queueConfirm, @Qualifier("exchangeConfirm") DirectExchange exchangeConfirm) {
        return BindingBuilder.bind(queueConfirm).to(exchangeConfirm).with(ROUTING_KEY);
    }
}
