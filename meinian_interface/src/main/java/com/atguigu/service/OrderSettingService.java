package com.atguigu.service;

import com.atguigu.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void addBatch(List<OrderSetting> listResult);

    List<Map<String, Object>> getMonthData(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
