package com.wjb.springboot.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b><code>RedisController</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/19 17:40.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot 0.1.0
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/string")
    public String test(){
        redisTemplate.opsForValue().set("name","Lucy");
        return (String) redisTemplate.opsForValue().get("name");
    }
}
