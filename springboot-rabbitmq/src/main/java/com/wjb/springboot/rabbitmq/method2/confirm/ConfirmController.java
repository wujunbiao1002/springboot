package com.wjb.springboot.rabbitmq.method2.confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * <b><code>ConfirmController</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/30 18:43.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */

@Slf4j
@RestController
@RequestMapping("/confirm")
public class ConfirmController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MyCallBack myCallBack;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(myCallBack);
    }

    @GetMapping("/{message}")
    public void sendMessage(@PathVariable String message) {
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.EXCHANGE_NAME, ConfirmConfig.ROUTING_KEY, message, correlationData);

        CorrelationData correlationData2 = new CorrelationData("2");
        rabbitTemplate.convertAndSend(ConfirmConfig.EXCHANGE_NAME, "key2", message, correlationData2);
        log.info("发送消息内容:{}", message);
    }
}
