package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.CustomException;
import com.itheima.entity.Category;
import com.itheima.entity.Dish;
import com.itheima.entity.Setmeal;
import com.itheima.mapper.CategoryMapper;
import com.itheima.service.CategoryService;
import com.itheima.service.DishService;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> queryWrapper_dish = new LambdaQueryWrapper();
        LambdaQueryWrapper<Setmeal> queryWrapper_setmeal = new LambdaQueryWrapper();

        queryWrapper_dish.eq(Dish::getCategoryId, id);
        int count_dish = dishService.count(queryWrapper_dish);

        if(count_dish > 0){
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        queryWrapper_setmeal.eq(Setmeal::getCategoryId, id);
        int count_setmeal = setmealService.count(queryWrapper_setmeal);

        if(count_setmeal > 0){
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        super.removeById(id);
    }
}
