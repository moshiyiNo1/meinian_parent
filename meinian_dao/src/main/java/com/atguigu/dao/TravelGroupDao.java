package com.atguigu.dao;

import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {
    void add(TravelGroup travelgroup);
    void addTravelGroupAndTravelItem(Map<String, Integer> map);
/*
    void addTravelGroupAndTravelItem(List list);
*/

    List<TravelItem> findItems();

    List<TravelGroup> findAll();

    List<TravelGroup> findTravelGroupById(Integer id);
}
