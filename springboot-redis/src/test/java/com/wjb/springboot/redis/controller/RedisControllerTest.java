package com.wjb.springboot.redis.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;

/**
 * <b><code>RedisControllerTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/20 11:52.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@SpringBootTest
class RedisControllerTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void test1() {
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

    @Test
    void test2() {
        redisTemplate.opsForList().leftPushAll("list","1","2","3");
    }

    @Test
    void test3() {
        Object o = redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set("name1","lucy1");
                operations.opsForValue().setIfAbsent("name","lucy2");
                operations.opsForValue().set("name3","lucy3");
                List exec = operations.exec();
                return exec;
            }
        });
        System.out.println(o.toString());
    }
}