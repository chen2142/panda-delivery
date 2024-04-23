package com.clx.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 菜品
 */
@Data
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //dish name
    private String name;


    //Dish category ID
    private Long categoryId;


    //The price of the dish
    private BigDecimal price;


    //Commodity
    private String code;


    //image
    private String image;


    //Descriptive information
    private String description;


    //0 discontinued 1 on sale
    private Integer status;


    //order
    private Integer sort;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //Whether to delete or not
    private Integer isDeleted;

}
