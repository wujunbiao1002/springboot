package com.wjb.springboot.elasticsearch.service.impl;

import com.wjb.springboot.elasticsearch.entity.User;
import com.wjb.springboot.elasticsearch.service.UserTemplateService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * <b><code>UserTemplateServiceImpl</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/3 15:25.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot 0.0.1
 */
@Service
public class UserTemplateServiceImpl implements UserTemplateService {
    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Override
    public SearchHits<User> listBykey(String key, Integer pageNum, Integer pageSize) {
        Query build = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", key))
                .withPageable(PageRequest.of(pageNum - 1, pageSize)).build();
        return restTemplate.search(build, User.class);
    }
}
