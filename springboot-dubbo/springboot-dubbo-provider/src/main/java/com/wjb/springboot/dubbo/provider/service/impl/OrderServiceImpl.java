package com.wjb.springboot.dubbo.provider.service.impl;

import com.wjb.springboot.dubbo.inerface.entity.Order;
import com.wjb.springboot.dubbo.inerface.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService
public class OrderServiceImpl implements OrderService {
    @Override
    public Order getOrder(String id) {
        log.info("请求id：{}", id);
        return new Order(id, "衣服", 199.99);
    }
}
