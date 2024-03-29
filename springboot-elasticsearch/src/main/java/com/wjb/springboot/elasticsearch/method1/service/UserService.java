package com.wjb.springboot.elasticsearch.method1.service;

import com.wjb.springboot.elasticsearch.method1.entity.User;
import org.springframework.data.domain.Page;

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

    void save(User user);

    void delete(String id);

    List<User> findByNameLike(String name);

    List<User> findByName(String name);

    List<User> searchByKey(String key);

    Page<User> findByDescription(String key, Integer pageNum, Integer pageSize);

    Page<User> searchSimilar(User user, Integer pageNum, Integer pageSize);
}
