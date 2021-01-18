package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travelItem")
public class TravelItemController {
    @Reference//调用远程接口，非本地接口，不使用Autowired
            //controller内使用，其他地方使用autowired
    TravelItemService travelItemService;

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")//权限校验
    //前端返回json数据，requestbody封装json数据到一个类对象，并传入方法
    public Result add(@RequestBody TravelItem travelItem){
        try {
            travelItemService.add(travelItem);
            //返回给页面的对象，页面仅需知晓是否成功，无需使用全参构造，此为成功添加
            return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/findPage")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")//权限校验
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        //根据前端传来的querypagebean的查询条件，查询返回获得的结果pageresult
        PageResult pageResult = travelItemService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE123')")//权限校验，使用TRAVELITEM_DELETE123测试
    public Result delete(Integer id){
        try {
            travelItemService.delete(id);
            return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result getTravelItemById(Integer id){
        try {
                TravelItem travelItem = travelItemService.getTravelItemById(id);
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAnyAuthority('TRAVELITEM_EDIT')")//权限校验
    public Result edit(@RequestBody TravelItem travelItem){
        try {
            travelItemService.edit(travelItem);
            return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS,travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
    }
}
