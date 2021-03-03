package com.wjb.springboot.datajpa.repository;

import com.wjb.springboot.datajpa.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <b><code>UserRepositoryTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/4 0:33.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot
 */
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void save(){
        for (int i = 0; i < 5; i++) {
            User user = User.builder().name("桔梗" + i).age((short) (i + 20)).description("美丽" + i).build();
            userRepository.save(user);
        }
    }

    @Test
    public void findById(){

    }
}