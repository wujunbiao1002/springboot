package com.wjb.springboot.dubbo.inerface.service;


import com.wjb.springboot.dubbo.inerface.entity.Order;

import java.util.concurrent.CompletableFuture;

public interface OrderService {

    /**
     * 获取订单信息
     *
     * @param id 订单id
     * @return 订单信息
     */
    Order getOrder(String id);

    default CompletableFuture<Order> getOrderAsync(String id) {
        return CompletableFuture.completedFuture(getOrder(id));
    }
}
