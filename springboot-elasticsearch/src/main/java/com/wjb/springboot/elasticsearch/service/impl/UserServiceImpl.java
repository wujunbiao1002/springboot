package com.wjb.springboot.elasticsearch.service.impl;

import com.wjb.springboot.elasticsearch.entity.User;
import com.wjb.springboot.elasticsearch.repository.UserRepository;
import com.wjb.springboot.elasticsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <b><code>UserServiceImpl</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/2 17:32.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot 0.0.1
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User blog) {
        return userRepository.save(blog);
    }

    @Override
    public void delete(User blog) {
        userRepository.delete(blog);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Page<User> findByName(String name, PageRequest pageRequest) {
        return userRepository.findByName(name, pageRequest);
    }
}
