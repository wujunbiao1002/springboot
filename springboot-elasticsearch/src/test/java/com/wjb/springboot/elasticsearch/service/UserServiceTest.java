package com.wjb.springboot.elasticsearch.service;

import com.wjb.springboot.elasticsearch.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <b><code>UserServiceTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/3 11:11.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot
 */
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void findById() {
    }

    @Test
    void save() {
        for (int i = 21; i <= 30; i++) {
            User i1 = User.builder()
                    .id(String.valueOf(i))
                    .age((short) (i + 1))
                    .name("神乐" + i)
                    .description("生气的火草" + i)
                    .build();
            userService.save(i1);
        }
    }

    @Test
    void delete() {
    }

    @Test
    void findByNameLike() {
        List<User> byNameLike = userService.findByNameLike("1");
        for (User user : byNameLike) {
            System.out.println(user.toString());
        }
    }

    @Test
    void findByName() {
        List<User> byNameLike = userService.findByName("桔梗2");
        for (User user : byNameLike) {
            System.out.println(user.toString());
        }
    }

    @Test
    void searchByKey() {
        List<User> byNameLike = userService.searchByKey("火草21");
        for (User user : byNameLike) {
            System.out.println(user.toString());
        }
    }

    @Test
    void findByDescription() {
        Page<User> all = userService.findByDescription("火草", 1, 2);
        System.out.println(all.getTotalPages());
        System.out.println(all.getTotalElements());
        for (User user : all) {
            System.out.println(user.toString());
        }
    }
    @Test
    void searchSimilar() {
        User user1 = User.builder().id("1").name("桔梗1").build();
        Page<User> all = userService.searchSimilar(user1,1, 2);
        System.out.println(all.getTotalPages());
        System.out.println(all.getTotalElements());
        for (User user : all) {
            System.out.println(user.toString());
        }
    }
}