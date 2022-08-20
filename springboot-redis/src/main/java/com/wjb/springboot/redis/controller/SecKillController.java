package com.wjb.springboot.redis.controller;

import com.wjb.springboot.redis.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <b><code>DoSecKillController</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/20 16:15.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot 0.1.0
 */
@RestController
@RequestMapping("sec-kill")
public class SecKillController {
    @Autowired
    private SecKillService secKillService;

    @GetMapping("/method1")
    public String secKillMethod1(){
        String uid = UUID.randomUUID().toString();
        boolean b = secKillService.doSecKill(uid, "1000");
        return "用户id：" + uid + " 秒杀"+(b?"成功":"失败");
    }

    @GetMapping("/method2")
    public String secKillMethod2(){
        String uid = UUID.randomUUID().toString();
        boolean b = secKillService.secKillLua(uid, "1000");
        return "用户id：" + uid + " 秒杀"+(b?"成功":"失败");
    }

}
