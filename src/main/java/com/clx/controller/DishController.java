package com.clx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.common.R;
import com.clx.dto.DishDto;
import com.clx.entity.Category;
import com.clx.entity.Dish;
import com.clx.entity.DishFlavor;
import com.clx.service.CategoryService;
import com.clx.service.DishFlavorService;
import com.clx.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Dishes management
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Add new dishes
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        //Clear cached data for all dishes
        //Set keys = redisTemplate.keys("dish_*");
        //redisTemplate.delete(keys);

        //Clear the cached data of dishes under a certain category
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);


        return R.success("Added new dishes successfully");
    }

    /**
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        // Add filter
        queryWrapper.like(name != null, Dish::getName, name);

        // Add sorting criteria
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        // Execute paging query
        dishService.page(pageInfo, queryWrapper);

        // OBJECT COPY
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map(item -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();// 分类id

            // Query classified objects based on id
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }


            return dishDto;
        }).toList();

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * Query dish information and corresponding taste information based on ID
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * Modify dishes
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        //Clear cached data for all dishes
        //Set keys = redisTemplate.keys("dish_*");
        //redisTemplate.delete(keys);

        //Clear the cached data of dishes under a certain category
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);

        return R.success("Modification of dishes successful");
    }

    /**
     * Query corresponding dish data based on conditions
     *
     * @param dish
     * @return
     */
/*    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        //Construct query conditions
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());

        //Add conditions to query status 1 (starting sales status)
        queryWrapper.eq(Dish::getStatus, 1);

        //Add sorting criteria
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        return R.success(list);
    }*/


    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        List<DishDto> dishDtoList = null;

        //Dynamically construct key
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();//dish_139555646_1

        //First get the cache data from redis
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        if (dishDtoList != null){
            //If it exists, return it directly without querying the database.
            return R.success(dishDtoList);
        }

        



        // Construct query conditions
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());

        // Add conditions to query status 1 (starting sales status)
        queryWrapper.eq(Dish::getStatus, 1);

        // Add sorting criteria
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        dishDtoList = list.stream().map(item -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();//Category id
            //Query classified objects based on id
            Category category = categoryService.getById(categoryId);

            if (category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //The ID of the current dish
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);

            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).toList();

        //If it does not exist, you need to query the database and cache the queried dish data to Redis.
        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);

        return R.success(dishDtoList);
    }
}
