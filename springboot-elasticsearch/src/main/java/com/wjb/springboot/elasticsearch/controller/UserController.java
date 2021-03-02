package com.wjb.springboot.elasticsearch.controller;

import com.wjb.springboot.elasticsearch.entity.User;
import com.wjb.springboot.elasticsearch.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * <b><code>UserController</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/2 17:41.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot 0.0.1
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户信息")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    public User getUserById(@PathVariable String id){
        Optional<User> byId = userService.findById(id);
        return byId.orElseGet(User::new);
    }

    @PostMapping
    @ApiOperation("增加数据")
    public String save(@RequestBody User user){
        userService.save(user);
        return "成功";
    }

    @DeleteMapping
    @ApiOperation("删除数据")
    public String delete(User user){
        userService.save(user);
        return "成功";
    }

    @GetMapping("/list")
    @ApiOperation("查询所有")
    public List<User> list(){
        return userService.findAll();
    }
}
