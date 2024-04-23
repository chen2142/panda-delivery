package com.clx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.entity.OrderDetail;
import com.clx.mapper.OrderDetailMapper;
import com.clx.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
