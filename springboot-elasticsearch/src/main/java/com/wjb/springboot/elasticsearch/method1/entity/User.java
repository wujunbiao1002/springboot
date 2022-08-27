package com.wjb.springboot.elasticsearch.method1.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "user", shards = 3, replicas = 2)
public class User {
    @Id
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("姓名")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    @ApiModelProperty("年龄")
    @Field(type = FieldType.Short)
    private short age;

    @ApiModelProperty("描述")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String description;
}
