package com.wjb.springboot.rabbitmq.method2.delayed;

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
@RequestMapping("/delayed")
public class DelayedController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/ttl/{message}/{ttlTime}")
    public String sendMsg(@PathVariable String message, @PathVariable String ttlTime ) {
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY,message, correlationData->{
            correlationData.getMessageProperties().setDelay(Integer.valueOf(ttlTime));
            return correlationData;
        });
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        return "已发送";
    }
}
