package com.clx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.entity.User;
import com.clx.mapper.UserMapper;
import com.clx.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
