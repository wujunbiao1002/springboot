package com.wjb.springboot.elasticsearch.repository;

import com.wjb.springboot.elasticsearch.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * <b><code>UserRepository</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/2 17:39.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot 0.0.1
 */
public interface UserRepository extends ElasticsearchRepository<User,String> {
}
