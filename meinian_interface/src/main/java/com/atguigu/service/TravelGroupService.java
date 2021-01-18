package com.atguigu.service;

import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;

import java.util.List;

public interface TravelGroupService {
    void add(Integer[] travelItemIds,TravelGroup travelgroup);

    List<TravelItem> findItems();

    List<TravelGroup> findAll();
}
