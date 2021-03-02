package com.wjb.springboot.elasticsearch.service;

import com.wjb.springboot.elasticsearch.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * <b><code>UserService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/2 17:32.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot 0.0.1
 */
public interface UserService {
    Optional<User> findById(String id);

    User save(User blog);

    void delete(User blog);

    List<User> findAll();

    Page<User> findByName(String name, PageRequest pageRequest);
}
