package com.clx.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * User Information
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    private String name;


    private String phone;


    //Gender 0 Female 1 Male
    private String sex;


    //Identification number
    private String idNumber;


    private String avatar;


    //Status 0: Disabled, 1: Normal
    private Integer status;
}
