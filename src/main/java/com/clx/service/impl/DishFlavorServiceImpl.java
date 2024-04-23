package com.clx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.entity.DishFlavor;
import com.clx.mapper.DishFlavorMapper;
import com.clx.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
