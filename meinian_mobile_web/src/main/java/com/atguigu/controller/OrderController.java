package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    JedisPool jedisPool;
    @Reference
    OrderService orderService;

    //已校验输入的验证码格式，现对验证码 判断是否正确
    @RequestMapping("/submitOrder")
    public Result submitOrder(@RequestBody Map map){//传入的为orderinfo(当前页面的客户信息，包含验证码)
        try {
            //验证码判断不需要进入dao层进行判断，可直接在controller层进行判断

            //校验验证码是否无误
            String telephone = (String)map.get("telephone");
            String validateCode = (String)map.get("validateCode");
            String codeFromRedis = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);
            //redis预存的验证码为空(验证码过期) 或 输入与之不匹配，返回false
            if (codeFromRedis==null || !codeFromRedis.equalsIgnoreCase(validateCode)){
                return new Result(false,MessageConstant.VALIDATECODE_ERROR);
            }
            //保存预约人信息
            return orderService.saveOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){ //订单id
        try {
            Map map = orderService.getSuccessInfoByOrderId(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
