package com.wjb.springboot.mybatisplus.generator.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <b><code>BaseEntity</code></b>
 * <p/>
 * Description 公共字段信息
 * <p/>
 * <b>Creation Time:</b> 2021/3/23 23:45.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot 0.1.0
 */
@Data
@ApiModel(value="公共字段信息", description="公共字段信息")
public class BaseEntity {
    @ApiModelProperty(value = "主键ID")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "表记录创建时间")
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;
}
