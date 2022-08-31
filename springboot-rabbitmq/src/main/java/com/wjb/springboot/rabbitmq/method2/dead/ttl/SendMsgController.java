package com.wjb.springboot.rabbitmq.method2.dead.ttl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <b><code>SendMsgConteroller</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/29 23:36.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Slf4j
@RestController
    @RequestMapping("/dead")
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/ttl/{message}")
    public String sendMsg(@PathVariable String message) {
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        rabbitTemplate.convertAndSend(TtlQueueConfig.X_EXCHANGE, "XA",message);
        rabbitTemplate.convertAndSend(TtlQueueConfig.X_EXCHANGE, "XB",message);
        return "已发送";
    }
    @GetMapping("/ttl/{message}/{ttlTime}")
    public String sendMsg(@PathVariable String message, @PathVariable String ttlTime ) {
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        rabbitTemplate.convertAndSend(TtlQueueConfig.X_EXCHANGE, "XC",message, correlationData->{
            correlationData.getMessageProperties().setExpiration(ttlTime);
            return correlationData;
        });
        return "已发送";
    }
}
