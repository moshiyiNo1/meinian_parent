package com.atguigu.service;

import com.atguigu.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    List<Setmeal> getSetmealAll();

    Setmeal getSetmealDetail(Integer id);

    Setmeal getSetMealById(Integer id);
}
