package com.wjb.springboot.redisson.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <b><code>SecKillServeiceTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/24 21:59.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@SpringBootTest
class SecKillServeiceTest {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void doSecKillCluster() {
        System.out.println(redisTemplate.opsForValue().get("sk1000:qt{sec}"));
//        redisTemplate.opsForValue().set("name","test");
    }

    @SneakyThrows
    @Test
    void test() {
        RLock lock = redissonClient.getLock("sk1000:locksec");
        lock.lock();
        System.out.println(lock.isLocked());
        Thread.sleep(10000);
        System.out.println(lock.isLocked());
    }
}