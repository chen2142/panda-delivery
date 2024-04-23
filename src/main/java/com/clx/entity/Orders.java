package com.clx.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //order number
    private String number;

    //Order status 1 Pending Payment, 2 Pending Delivery, 3 Dispatched, 4 Completed, 5 Cancelled
    private Integer status;


    //The ID of the user who placed the order
    private Long userId;

    //Address ID
    private Long addressBookId;


    //Time when the order was placed
    private LocalDateTime orderTime;


    //Checkout time
    private LocalDateTime checkoutTime;


    //Payment methods: 1 WeChat, 2 Alipay
    private Integer payMethod;


    //Paid-in amount
    private BigDecimal amount;

    //remark
    private String remark;

    //Username
    private String userName;

    //Mobile phone number
    private String phone;

    //address
    private String address;

    //consignee
    private String consignee;
}
