package com.wjb.springboot.elasticsearch.service;

import com.wjb.springboot.elasticsearch.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

/**
 * <b><code>UserTemplateServiceTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/3 15:37.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot
 */
@SpringBootTest
class UserTemplateServiceTest {
    @Autowired
    private UserTemplateService userTemplateService;

    @Test
    void listBykey() {
        SearchHits<User> userSearchHits = userTemplateService.listBykey("神乐", 1, 3);
        System.out.println(userSearchHits.getTotalHits());
        for (SearchHit<User> searchHit : userSearchHits.getSearchHits()) {
            System.out.println(searchHit);
        }
    }
}