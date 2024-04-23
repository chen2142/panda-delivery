package com.clx.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ADDRESS BOOK
 */
@Data
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //User ID
    private Long userId;


    //consignee
    private String consignee;


    //Mobile phone number
    private String phone;


    //Gender 0 Female 1 Male
    private String sex;


    //Provincial district number
    private String provinceCode;


    //Provincial name
    private String provinceName;


    //Municipal district number
    private String cityCode;


    //Municipal name
    private String cityName;


    //District-level district number
    private String districtCode;


    //District-level name
    private String districtName;


    //Full address
    private String detail;


    //Label
    private String label;

    //Default 0 No 1 Yes
    private Integer isDefault;

    //Creation time
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    //Updated time
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
