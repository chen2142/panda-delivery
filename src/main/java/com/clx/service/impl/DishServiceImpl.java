package com.clx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.dto.DishDto;
import com.clx.entity.Dish;
import com.clx.entity.DishFlavor;
import com.clx.mapper.DishMapper;
import com.clx.service.DishFlavorService;
import com.clx.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    DishFlavorService dishFlavorService;

    /**
     * Add a new dish and save the corresponding flavor data
     * @param dishDto
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {

        //Save the basic information of the dish to the dish list
        this.save(dishDto);

        Long dishId = dishDto.getId();//dish id

        //Taste of the dish
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item -> {
            item.setDishId(dishId);
            return item;
        }).toList();

        //Save the dish taste data to the dish_flavor table
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * Query the dish information and the corresponding taste information according to the ID
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //To query the basic information of a dish, you can query it from the DISH table
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        //Query the flavor information of the current dish from the dish flavor table
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //Update the basic information of the DISH table
        this.updateById(dishDto);//polymorphism

        //Clear the flavor data corresponding to the current dish -- delete operation in the dish flavor table
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        //Add the currently submitted flavor data --dish flavor table insert operation
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map(item -> {
            item.setDishId(dishDto.getId());
            return item;
        }).toList();

        dishFlavorService.saveBatch(flavors);
    }
}
