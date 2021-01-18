package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    List<OrderSetting> getMonthData(Map<String, String> param);

    //根据日期查询是否已设置当前日期预约状态
    OrderSetting findOrderSetting(Date orderDate);
    //保存OrderSetting
    void add(OrderSetting orderSetting);

    //根据日期更新number
    void editNumberByOrderDate(OrderSetting orderSetting);

    OrderSetting findOrderSettingByOrderDate(Date date);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
