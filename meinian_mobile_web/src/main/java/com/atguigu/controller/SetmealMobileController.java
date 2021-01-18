package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
//不同消费者访问同一个提供者，setmeal可重复，不会串controller
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    SetmealService setmealService;

    //查找所有套餐信息  setmeal.html
    @RequestMapping("/getSetmeal")
    public Result getSetmealAll(){
        List<Setmeal> list = setmealService.getSetmealAll();
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
    }

    //点击查看某套餐   setmeal_detail.html
    @RequestMapping("/findById")
    public Result getSetmealDetail(Integer id){
        try {
            Setmeal setmeal = setmealService.getSetmealDetail(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    //预约套餐。，点击预约之后的界面
    @RequestMapping("/getSetMealById")
    public Result getSetMealById(Integer id){
        Setmeal setmeal = setmealService.getSetMealById(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
