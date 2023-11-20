package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.CustomException;
import com.itheima.dto.SetmealDto;
import com.itheima.entity.Setmeal;
import com.itheima.entity.SetmealDish;
import com.itheima.mapper.SetmealMapper;
import com.itheima.service.SetmealDishService;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto){
        this.save(setmealDto);

        List<SetmealDish> list = setmealDto.getSetmealDishes();
        Long setmealId = setmealDto.getId();

        list = list.stream().map(item -> {
            item.setSetmealId(setmealId);
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(list);
    }

    /**
     * 删除套餐
     * @param ids
     */
    public void deleteWith(List<Long> ids){
        LambdaQueryWrapper<Setmeal> queryWrapperSetmeal = new LambdaQueryWrapper<>();
        queryWrapperSetmeal.in(Setmeal::getId, ids);
        queryWrapperSetmeal.eq(Setmeal::getStatus, 1);

        int count = this.count(queryWrapperSetmeal);
        if(count > 0){
            throw  new CustomException("有正在售卖的商品，不能进行批量删除！");
        }

        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> queryWrapperSetmealDish = new LambdaQueryWrapper<>();
        queryWrapperSetmealDish.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(queryWrapperSetmealDish);

    }
}
