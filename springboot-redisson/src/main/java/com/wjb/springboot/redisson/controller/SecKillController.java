package com.wjb.springboot.redisson.controller;

import com.wjb.springboot.redisson.service.SecKillServeice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <b><code>SecKillController</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/24 16:21.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@RestController
@RequestMapping("/sec-kill")
public class SecKillController {

    @Autowired
    private SecKillServeice secKillServeice;

    @GetMapping
    public String secKill() {
        String uid = UUID.randomUUID().toString();
        boolean b = secKillServeice.doSecKillCluster(uid, "1000");
        return "用户id:" + uid + "抢购" + (b ? "成功" : "失败");
    }

}
