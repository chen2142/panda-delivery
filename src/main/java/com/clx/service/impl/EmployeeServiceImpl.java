package com.clx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.entity.Employee;
import com.clx.mapper.EmployeeMapper;
import com.clx.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{

}
