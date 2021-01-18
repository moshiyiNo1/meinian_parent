package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;

import java.util.List;

public interface SetmealDao {
    List<Setmeal> getSetmealAll();

    Setmeal getSetmealDetail(Integer id);

    Setmeal getSetMealById(Integer id);
}
