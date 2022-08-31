package com.wjb.springboot.rabbitmq.method2.dead.ttl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b><code>TtlQueueConfig</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/29 23:23.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
@Configuration
public class TtlQueueConfig {
    public static final String X_EXCHANGE = "X";
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";

    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    public static final String DEAD_LETTER_QUEUE = "QD";

    // 声明交换机X
    @Bean
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    // 声明交换机Y
    @Bean
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    // 声明队列QA ttl 10s,设置死信交换机和路由key
    @Bean
    public Queue queueA() {
        return QueueBuilder.durable(QUEUE_A).ttl(10000).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("YD").build();
    }

    // 声明队列QB ttl 40s,设置死信交换机和路由key
    @Bean
    public Queue queueB() {
        return QueueBuilder.durable(QUEUE_B).ttl(40000).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("YD").build();
    }
    // 声明队列QC,设置死信交换机和路由key
    @Bean
    public Queue queueC() {
        return QueueBuilder.durable(QUEUE_C).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("YD").build();
    }
    // 声明队列QD
    @Bean
    public Queue queueD() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    // 队列A绑定交换机X
    @Bean
    public Binding queueABindingXExchange(@Qualifier("queueA") Queue queueA, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    // 队列B绑定交换机X
    @Bean
    public Binding queueBBindingXExchange(@Qualifier("queueB") Queue queueB, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    // 队列C绑定交换机X
    @Bean
    public Binding queueCBindingXExchange(@Qualifier("queueC") Queue queueC, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }

    // 死信队列D绑定死信交换机Y
    @Bean
    public Binding queueDBindingYExchange(@Qualifier("queueD") Queue queueD, @Qualifier("yExchange") DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }
}
