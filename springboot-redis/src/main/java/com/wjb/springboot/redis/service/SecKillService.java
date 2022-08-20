package com.wjb.springboot.redis.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>DoSecKillService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/20 16:24.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Service
public class SecKillService {

    private final RedisTemplate redisTemplate;

    public SecKillService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //秒杀过程
    @SneakyThrows
    public boolean doSecKill(String uid, String prodId) {
        //1 uid和prodId非空判断
        if (uid == null || prodId == null) {
            return false;
        }

        //2 拼接key
        // 2.1 库存key
        String kcKey = "sk" + prodId + ":qt";
        // 2.2 秒杀成功用户key
        String userKey = "sk" + prodId + ":user";

        // 使用事务 保证在一个session中
        Object result =  redisTemplate.execute(new SessionCallback() {
            @SneakyThrows
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                // 3.监视库存
                operations.watch(kcKey);

                // 4.获取库存，如果库存null，秒杀还没有开始
                Object kc = operations.opsForValue().get(kcKey);
                if (kc == null) {
                    System.out.println("用户id：" + uid + "秒杀还没有开始，请等待");
                    return false;
                }
                // 5.判断如果商品数量，库存数量小于1，秒杀结束
                if (Integer.parseInt(kc.toString()) <= 0) {
                    System.out.println("用户id：" + uid + "秒杀已经结束");
                    return false;
                }
                // 6.判断用户是否重复秒杀操作
                Boolean member = operations.opsForSet().isMember(userKey, uid);
                if (member) {
                    System.out.println("用户id：" + uid + "已经秒杀成功，不能重复秒杀");
                    return false;
                }
                // 7.秒杀过程
                operations.multi();
                operations.opsForValue().decrement(kcKey);
                operations.opsForSet().add(userKey, uid);
                return operations.exec();
            }
        });

        if (result == null) {
            System.out.println("用户id：" + uid + "秒杀失败" + result.toString());
            return false;
        }
        System.out.println("用户id：" + uid + "秒杀成功");
        return true;
    }

    @Bean
    public DefaultRedisScript<Long> secKillScript() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("script/seckill.lua")));
        return defaultRedisScript;
    }

    @Autowired
    private RedisScript redisScript;

    public boolean secKillLua(String uid, String prodId) {
        //用户
        String kcKey = "sk" + prodId + ":qt";
        String userKey = "sk" + prodId + ":user";
        // 0: 库存不足 1: 秒杀成功 2: 已经秒杀过
        List<String> keys = new ArrayList<>();
        keys.add(kcKey);
        keys.add(userKey);
        Long result = (Long) redisTemplate.execute(redisScript, keys, uid);
        if (result == 0) {
            System.out.println("库存不足");
            return false;
        } else if (result == 1) {
            System.out.println("用户id：" + uid + "秒杀成功");
            return true;
        } else if (result == 2) {
            System.out.println("用户id：" + uid + "已经秒杀过");
            return false;
        } else {
            System.out.println();
            return false;
        }
    }
}
