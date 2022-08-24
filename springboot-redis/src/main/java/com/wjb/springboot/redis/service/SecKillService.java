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

import java.util.*;
import java.util.concurrent.TimeUnit;

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

    private final RedisTemplate<String, String> redisTemplate;

    public SecKillService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 单机操作
     *
     * @param uid    用户id
     * @param prodId 商品id
     * @return 结果
     */
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
        Object result = redisTemplate.execute(new SessionCallback() {
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

    /**
     * 集群
     * redis锁setnx ex
     * 重点
     * @param uid    用户id
     * @param prodId 商品id
     */
    public void doSecKillCluster(String uid, String prodId) {
        //1 uid和prodId非空判断
        if (uid == null || prodId == null) {
            throw new IllegalArgumentException();
        }
        //2 拼接key
        // 2.1 库存key
        String kcKey = "sk" + prodId + ":qt{sec}";
        String userKey = "sk" + prodId + ":user{sec}";
        // 防止卡顿，超时释放锁，锁功能失效
        String lockV = UUID.randomUUID().toString();
        // 上锁
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", lockV, 1, TimeUnit.SECONDS);

        if (Boolean.TRUE.equals(lock)) {
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
                throw new IllegalArgumentException();
            } finally {
               //  释放锁 通过lua脚本保证释放锁的操作原子性
                DefaultRedisScript<Long> script = new DefaultRedisScript<>();
                script.setResultType(Long.class);
                script.setScriptText("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end");
                redisTemplate.execute(script, Collections.singletonList("lock"), lockV);
            }
        } else {
            try {
                Thread.sleep(100);
                doSecKillCluster(uid, prodId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Bean
    public DefaultRedisScript<Long> secKillLua() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("script/seckill.lua")));
        return defaultRedisScript;
    }

    @Autowired
    private RedisScript redisScript;

    /**
     * 单机lua操作
     *
     * @param uid    用户id
     * @param prodId 商品id
     * @return 结果
     */
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

    /**
     * 集群lua操作
     *
     * @param uid    用户id
     * @param prodId 商品id
     * @return 结果
     */
    public boolean doSecKillClusterLua(String uid, String prodId) {
        //用户
        //key采用{} 保证key落在同一个slot
        String kcKey = "sk" + prodId + ":qt{sec}";
        String userKey = "sk" + prodId + ":user{sec}";
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
