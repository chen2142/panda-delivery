package com.clx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
