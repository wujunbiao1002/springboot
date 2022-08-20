package com.wjb.springboot.redis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * <b><code>SecKillServiceTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/20 16:46.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@SpringBootTest
class SecKillServiceTest {

    @Autowired
    private SecKillService secKillService;

    @Test
    void doSecKillTest() {
        int i = 0;
        while (++i < 10) {

           Thread thread =  new Thread(() -> {
               System.out.println("线程开始" + Thread.currentThread().getName());
               boolean b = secKillService.doSecKill(UUID.randomUUID().toString(), "1000");
               System.out.println(b);
           });
            thread.start();

        }
    }
}