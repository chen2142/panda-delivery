package com.clx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.common.CustomException;
import com.clx.dto.SetmealDto;
import com.clx.entity.Setmeal;
import com.clx.entity.SetmealDish;
import com.clx.mapper.SetmealMapper;
import com.clx.service.SetmealDishService;
import com.clx.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    SetmealDishService setmealDishService;

    /**
     * When you add a package, you need to save the relationship between the package and the dish
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //Save the basic information of the package, run the setmeal operation,
        // and execute the insert operation
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).toList();

        //Save the information associated with the set meal and the dish,
        // run the setmeal dish operation, and execute the insert operation
        setmealDishService.saveBatch(setmealDishes);

    }

    /**
     * When you delete a package, you can delete the associated dish data
     * @param ids
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //Query the status of the offer to determine whether it can be deleted
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(queryWrapper);

        if (count > 0){
            //If it cannot be deleted, a service exception is thrown
            throw new CustomException("The package is on sale and cannot be deleted");
        }

        //If you can delete it, delete the data in the plan first---setmeal
        this.removeByIds(ids);

        //Deletes the data in the relational table---setmeal_dish
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);
    }
}
