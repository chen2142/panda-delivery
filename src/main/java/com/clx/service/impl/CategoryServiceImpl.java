package com.clx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.common.CustomException;
import com.clx.entity.Category;
import com.clx.entity.Dish;
import com.clx.entity.Setmeal;
import com.clx.mapper.CategoryMapper;
import com.clx.service.CategoryService;
import com.clx.service.DishService;
import com.clx.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * need to make a judgment before deleting a classification based on your ID
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //Add search conditions based on the category ID
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);


        //Query whether the current category is associated with the dish,
        // and if it is, a service exception will be thrown
        if (count1 > 0){
            //The dish has been associated, and a business exception has been thrown
            throw new CustomException("Dishes associated with the current category cannot be deleted");
        }

        //Query whether the current category is associated with a package,
        // and if it is, a service exception is thrown
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0){
            //A package has been associated and a business exception is thrown
            throw new CustomException("The plan is associated with the current category and cannot be deleted");
        }



        //Delete categories as normal
        super.removeById(id);
    }
}
