package com.wjb.springboot.elasticsearch.method1.repository;

import com.wjb.springboot.elasticsearch.method1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

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
public interface UserRepository extends ElasticsearchRepository<User, String> {

    List<User> findByNameLike(String name);

    List<User> findByName(String name);

    Page<User> findByDescription(String content, Pageable pageable);

    @Highlight(fields = {@HighlightField(name = "description", parameters = @HighlightParameters(requireFieldMatch = false)),},
            parameters = @HighlightParameters(preTags = "<strong><font style='color:red'>", postTags = "</font></strong>")
    )
    SearchPage<User> findAllByDescriptionIsLike(String content, Pageable pageable);
}
