package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    //上传模板，对预约信息进行统一设置，参数为封装的对象的集合
    public void addBatch(List<OrderSetting> listResult){
        //1.遍历List<OrderSetting> list
        for (OrderSetting orderSetting : listResult) {
            //2.判断当前的日期之前是否设置过
            OrderSetting os = orderSettingDao.findOrderSetting(orderSetting.getOrderDate());
            //	2.2 没有设置过, 保存
            if (os==null){
                orderSettingDao.add(orderSetting);
            //	2.1 设置过, 更新数量
            }else {
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }
        }
    }

    @Override
    public List<Map<String, Object>> getMonthData(String date) {
        //查询？月数据：between‘？-1’ and ‘？-31’
        String startDate = date + "-1";
        String endDate = date + "-31";
        //封装查询条件
        Map<String, String> param = new HashMap<>();
        param.put("startDate",startDate);
        param.put("endDate",endDate);

        List<OrderSetting> list = orderSettingDao.getMonthData(param);
        List<Map<String, Object>> data = new ArrayList<>();

        for (OrderSetting orderSetting : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            data.add(map);
        }
        return data;
    }

    @Override
    //单击设置进行设置，参数为封装的对象
    public void editNumberByDate(OrderSetting orderSetting) {
        OrderSetting os = orderSettingDao.findOrderSetting(orderSetting.getOrderDate());
        if (os==null){
            orderSettingDao.add(orderSetting);
        }else {
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }

    }
}
