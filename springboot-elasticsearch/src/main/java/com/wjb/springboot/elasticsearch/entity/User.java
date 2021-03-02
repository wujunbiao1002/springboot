package com.wjb.springboot.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * <b><code>User</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/2 17:32.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot 0.0.1
 */
@Data
@Document(indexName = "user", indexStoreType = "user" )
public class User {
    @Id
    private String id;
    private String name;
    private short age;
    private String description;
}
