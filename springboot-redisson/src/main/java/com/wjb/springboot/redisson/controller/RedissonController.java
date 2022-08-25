package com.wjb.springboot.redisson.controller;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b><code>RedissonController</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/24 16:28.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@RestController
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/redisson/{key}")
    public String redissonTest(@PathVariable("key") String lockKey) {

        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            RedisOperations operations = redisTemplate.opsForHash().getOperations();
            System.out.println(operations.opsForHash().values(lockKey));
            Thread.sleep(20000);
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return "已解锁";
    }
}
