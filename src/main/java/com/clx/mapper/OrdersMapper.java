package com.clx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
