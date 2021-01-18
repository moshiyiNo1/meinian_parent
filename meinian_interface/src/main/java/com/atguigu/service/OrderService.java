package com.atguigu.service;

import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;

import java.util.Map;

public interface OrderService {

    Result saveOrder(Map map) throws Exception;

    Map getSuccessInfoByOrderId(Integer id);
}
