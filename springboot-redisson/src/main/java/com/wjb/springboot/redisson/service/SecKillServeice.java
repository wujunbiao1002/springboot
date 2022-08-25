package com.wjb.springboot.redisson.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <b><code>SecKillServeice</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/24 16:21.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Service
public class SecKillServeice {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param uid    用户id
     * @param prodId 商品id
     * @return 结果
     */
    public boolean doSecKillCluster(String uid, String prodId) {
        if (uid == null || prodId == null) {
            throw new IllegalArgumentException();
        }

        String kcKey = "sk" + prodId + ":qt{sec}";
        String userKey = "sk" + prodId + ":user{sec}";
        // 上锁
        RLock lock = redissonClient.getLock("sk" + prodId + ":lock{sec}");
        try {
            if (lock.tryLock(10, 10, TimeUnit.SECONDS)) {
                try {
                    // 4.获取库存，如果库存null，秒杀还没有开始
                    Object kc = redisTemplate.opsForValue().get(kcKey);
                    if (kc == null) {
                        System.out.println("用户id：" + uid + "秒杀还没有开始，请等待");
                        throw new IllegalArgumentException();
                    }
                    // 5.判断如果商品数量，库存数量小于1，秒杀结束
                    if (Integer.parseInt(kc.toString()) <= 0) {
                        System.out.println("用户id：" + uid + "秒杀已经结束");
                        throw new IllegalArgumentException();
                    }
                    // 6.判断用户是否重复秒杀操作
                    Boolean member = redisTemplate.opsForSet().isMember(userKey, uid);
                    if (Boolean.TRUE.equals(member)) {
                        System.out.println("用户id：" + uid + "已经秒杀成功，不能重复秒杀");
                        throw new IllegalArgumentException();
                    }
                    // 7.秒杀过程
                    redisTemplate.opsForValue().decrement(kcKey);
                    redisTemplate.opsForSet().add(userKey, uid);

                    System.out.println("用户id：" + uid + "秒杀成功");
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        return  false;
    }
}
