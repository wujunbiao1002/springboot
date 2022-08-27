package com.wjb.springboot.elasticsearch.method1.repository;

import com.wjb.springboot.elasticsearch.method1.entity.User;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.Optional;

/**
 * <b><code>UserRepositoryTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/27 17:29.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User user = new User().builder().id("2000").name("赵路思").age((short) 54).description("实现价值最大化，优化用户体验Elastic 提供的每款产品分分钟即可部署到任何云中。了解详情 ").build();
        userRepository.save(user);
    }

    @Test
    void update(){
        Optional<User> user = userRepository.findById("2000");
        User user1 = user.get();
        user1.setAge((short) 18);
        userRepository.save(user1);
    }

    @Test
    void findAll(){
        Iterable<User> all = userRepository.findAll();
        for (User user : all) {
            System.out.println(user.toString());
        }
    }
    @Test
    void findByPageable(){
        Sort sort = Sort.by(Sort.Direction.ASC, "_id");
        int currentPage = 0;
        int pageSize = 5;
        PageRequest request = PageRequest.of(currentPage, pageSize, sort);
        Page<User> all = userRepository.findAll(request);
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    void termQuery(){
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name", "1000");

        Iterable<User> search = userRepository.search(queryBuilder);
        for (User user : search) {
            System.out.println(user);
        }
    }

    @Test
    void highlighter(){
        PageRequest of = PageRequest.of(0, 10);
        SearchPage<User> hits = userRepository.findAllByDescriptionIsLike("最大化", of);
        for (SearchHit<User> hit : hits) {
            System.out.println(hit);
        }

    }
}