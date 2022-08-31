package com.wjb.springboot.rabbitmq.method2.back;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <b><code>BackConfig</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/31 13:12.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
@Configuration
public class BackConfig {
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    public static final String CONFIRM_ROUTING_KEY = "key1";
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";
    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    public static final String WARNING_QUEUE_NAME = "warning.queue";

    @Bean
    public DirectExchange confirmBackExchange() {
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true).withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME).build();
    }

    @Bean
    public FanoutExchange backExchange() {
        return ExchangeBuilder.fanoutExchange(BACKUP_EXCHANGE_NAME).durable(true).build();
    }

    @Bean
    public Queue confirmBackQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Queue backQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    @Bean
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    @Bean
    public Binding confirmBackQueueBindingConfirmBackExchange(@Qualifier("confirmBackQueue") Queue confirmBackQueue,
                                                              @Qualifier("confirmBackExchange") DirectExchange confirmBackExchange) {
        return BindingBuilder.bind(confirmBackQueue).to(confirmBackExchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    public Binding backBinding(@Qualifier("backQueue") Queue backQueue, @Qualifier("backExchange") FanoutExchange backExchange) {
        return BindingBuilder.bind(backQueue).to(backExchange);
    }

    @Bean
    public Binding waringBinding(@Qualifier("warningQueue") Queue warningQueue, @Qualifier("backExchange") FanoutExchange backExchange) {
        return BindingBuilder.bind(warningQueue).to(backExchange);
    }
}
