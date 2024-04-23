package com.clx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.common.R;
import com.clx.entity.Category;
import com.clx.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classification management
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Add new category
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");

    }

    /**
     * Paging query
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        //Pagination constructor
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //conditional constructor
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //Add sorting conditions
        queryWrapper.orderByAsc(Category::getSort);

        //Perform paging query
        categoryService.page(pageInfo, queryWrapper);


        return R.success(pageInfo);
    }

    /**
     * Delete categories based on id
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        log.info("Delete category，id：{}", id);

        // categoryService.removeById(id);
        categoryService.remove(id);

        return R.success("Classified information deleted successfully");
    }


    /**
     * Modify classification information based on id
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("Modify classification information");

        categoryService.updateById(category);
        return R.success("Modification of classification information successful");
    }

    /**
     * Query classified data based on conditions
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);
    }

}
