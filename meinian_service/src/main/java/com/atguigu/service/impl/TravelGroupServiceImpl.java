package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = TravelGroupService.class)
public class TravelGroupServiceImpl implements TravelGroupService {
    @Autowired
    TravelGroupDao travelGroupDao;

    @Override
    public List<TravelGroup> findAll() {
        return travelGroupDao.findAll();
    }

    @Override
    public void add(Integer[] travelItemIds,TravelGroup travelgroup) {
        travelGroupDao.add(travelgroup);
        if (travelItemIds!=null && travelItemIds.length>0){
            for (Integer travelItemId : travelItemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("travelItemId",travelItemId);
                map.put("travelGroupId",travelgroup.getId());
                travelGroupDao.addTravelGroupAndTravelItem(map);
            }

/*
            for (Integer travelItemId : travelItemIds) {
                List list = new ArrayList();
                list.add(travelgroup.getId(),travelItemId);
                travelGroupDao.addTravelGroupAndTravelItem(list);
            }
*/
        }

    }

    @Override
    public List<TravelItem> findItems() {
        return travelGroupDao.findItems();
    }
}
