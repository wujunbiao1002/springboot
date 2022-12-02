package com.wjb.springboot.dubbo.consumer.controller;

import com.wjb.springboot.dubbo.inerface.entity.Order;
import com.wjb.springboot.dubbo.inerface.service.OrderService;
import lombok.SneakyThrows;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class OrderController {
    @DubboReference
    private OrderService orderService;

    @GetMapping("/order/{id}")
    public Order getOrder(@PathVariable String id){
        return orderService.getOrder(id);
    }

    @SneakyThrows
    @GetMapping("/orderAsync/{id}")
    public Order getOrderAsync(@PathVariable String id){
        CompletableFuture<Order> orderAsync = orderService.getOrderAsync(id);
        return orderAsync.get();
    }
}
