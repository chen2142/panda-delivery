package com.clx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.common.R;
import com.clx.entity.Employee;
import com.clx.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Employee login
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        //1 Encrypt the password submitted on the page with md5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2 Query the database based on the username username submitted on the page
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3 If no query is found, a login failure result will be returned.
        if (emp == null){
            return R.error("Login failed");
        }

        //4 Password comparison, if inconsistent, return login failure result
        if (!emp.getPassword().equals(password)){
            return R.error("Login failed");
        }

        //5 Check the employee status. If it is disabled, return the result that the employee is disabled.
        if (emp.getStatus() == 0){
            return R.error("Account disabled");
        }

        //6 If the login is successful, the employee ID is stored in the Session and the login success result is returned.
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);

    }

    /**
     * employee exit
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //Clear the ID of the currently logged-in employee saved in the Session
        request.getSession().removeAttribute("employee");
        return R.success("EXIT SUCCESSFULLY");
    }

    /**
     * Add new employees
     * @param request
     * @param employee
     * @return
     */
    @PostMapping()
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("Add new employees");

        //Set the initial password to 123456, which requires md 5 encryption.
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());

        //Get the id of the currently logged-in user
        // Long empId = (Long) request.getSession().getAttribute("employee");

        // employee.setCreateUser(empId);
        // employee.setUpdateUser(empId);

        employeeService.save(employee);



        return R.success("Added employee successfully");
    }

    /**
     * Employee information page query
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize, String name){
        log.info("page = {}, pageSize = {}, name = {}",page, pageSize, name );

        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getUsername, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);


        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * Modify employee information based on ID
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info(employee.toString());

        // Long empId = (Long) request.getSession().getAttribute("employee");
        // employee.setUpdateTime(LocalDateTime.now());
        // employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("Employee information modified successfully");
    }

    /**
     * Query employee information based on ID
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("Query employee information based on ID...");
        Employee employee = employeeService.getById(id);
        if (employee != null){
            return R.success(employee);
        }
        return R.error("No corresponding employee information found");
    }

}
