package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelGroupService;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travelGroup")
public class TravelGroupController {
    @Reference
    TravelGroupService travelGroupService;

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<TravelGroup> list = travelGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(Integer[] travelItemIds,@RequestBody TravelGroup travelgroup){
        try {
            travelGroupService.add(travelItemIds,travelgroup);
            return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/findItems")
    public Result findItems(){
        try {
            List<TravelItem> travelItems = travelGroupService.findItems();
            return new Result(true,"",travelItems);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询自由行列表失败");
        }
    }
}
