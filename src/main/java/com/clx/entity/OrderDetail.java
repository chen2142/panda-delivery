package com.clx.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细
 */
@Data
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //name
    private String name;

    //order id
    private Long orderId;


    //dish id
    private Long dishId;


    //set meal id
    private Long setmealId;


    //flavor
    private String dishFlavor;


    //amount
    private Integer number;

    //price amount
    private BigDecimal amount;

    //pic
    private String image;
}
