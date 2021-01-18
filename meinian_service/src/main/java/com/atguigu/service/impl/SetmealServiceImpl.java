package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.SetmealDao;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;

    @Override
    public List<Setmeal> getSetmealAll() {
        return setmealDao.getSetmealAll();
    }

    @Override
    public Setmeal getSetmealDetail(Integer id) {
        return setmealDao.getSetmealDetail(id);
    }

    @Override
    public Setmeal getSetMealById(Integer id) {
        return setmealDao.getSetMealById(id);
    }
}
