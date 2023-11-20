package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.dto.DishDto;
import com.itheima.entity.Dish;
import org.springframework.web.bind.annotation.RequestBody;

public interface DishService extends IService<Dish> {

    /**
     * 新增菜品
     */
    public void saveWithFlavor(DishDto dishDto);

    /**
     * 修改菜品
     * @param dishDto
     */
    public void updateWithFlavor(@RequestBody DishDto dishDto);

    /**
     * 查询单个菜品
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id);
}
