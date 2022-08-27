package com.wjb.springboot.elasticsearch.method1.service;

import com.wjb.springboot.elasticsearch.method1.entity.User;
import org.springframework.data.elasticsearch.core.SearchHits;

/**
 * <b><code>UserTemplateService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/3 15:25.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot 0.0.1
 */
public interface UserTemplateService {

    SearchHits<User> listBykey(String key, Integer pageNum, Integer pageSize);
}
