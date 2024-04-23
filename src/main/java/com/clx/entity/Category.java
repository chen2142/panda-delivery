package com.clx.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //Type 1 Dish Category 2 Set Meal Category
    private Integer type;


    //Classification name
    private String name;


    //order
    private Integer sort;


    //Creation time
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    //updated time
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    //Created by
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    //Modified by
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //Whether to delete or not
    private Integer isDeleted;

}
