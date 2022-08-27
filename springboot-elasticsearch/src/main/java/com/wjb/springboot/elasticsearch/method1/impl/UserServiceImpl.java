package com.wjb.springboot.elasticsearch.method1.impl;

import com.wjb.springboot.elasticsearch.method1.entity.User;
import com.wjb.springboot.elasticsearch.method1.repository.UserRepository;
import com.wjb.springboot.elasticsearch.method1.service.UserService;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findByNameLike(String name) {
        return userRepository.findByNameLike(name);
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> searchByKey(String key) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(key);
        System.out.println("查询的语句:" + builder);
        Iterable<User> searchResult = userRepository.search(builder);
        Iterator<User> iterator = searchResult.iterator();
        List<User> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    @Override
    public Page<User> findByDescription(String key, Integer pageNum, Integer pageSize) {

        return userRepository.findByDescription(key, PageRequest.of(pageNum - 1, pageSize));
    }

    @Override
    public Page<User> searchSimilar(User user, Integer pageNum, Integer pageSize) {
        return userRepository.searchSimilar(user, new String[]{"name"}, PageRequest.of(pageNum - 1, pageSize));
    }
}
