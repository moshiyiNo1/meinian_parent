package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    MemberDao memberDao;
    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public Result saveOrder(Map map) throws Exception {
        //1.判断当前日期是否可预约
        String orderDate = (String)map.get("orderDate");
            //将string类型转换为date型
        Date date = DateUtils.parseString2Date(orderDate);
            //根据日期查询当前的预约情况
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByOrderDate(date);
        if (orderSetting==null){//当前日期不开放预约，返回false
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);

        //2.可预约，查询预约情况
        }else {
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            if (reservations>=number){//已预约人数大于等于上限，预约已满情况
                return new Result(false,MessageConstant.ORDER_FULL);
            }
        }

        //3.判断该用户是否为会员
        String tel = (String)map.get("telephone");
        String setmealId = (String)map.get("setmealId");
            //会员库中查询是否为会员
        Member member = memberDao.getMemberByTel(tel);
        if (member!=null){//为会员
            Order order = new Order(member.getId(),date,null,null,Integer.parseInt(setmealId));
            List<Order> list = orderDao.findOrderByCondition(order);
            //在已预约表中查找是否有该预约信息，有为非空 且 长度大于零
            if (list!=null && list.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else {//非会员，自动注册
            member = new Member();
            member.setName((String)map.get("name"));
            member.setSex((String)map.get("sex"));
            member.setIdCard((String)map.get("idCard"));
            member.setPhoneNumber(tel);
            member.setRegTime(new Date());
            memberDao.add(member);
        }

        //4.进行预约
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date); //是预约哪一天玩，不是当前系统时间
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(Order.ORDERTYPE_WEIXIN);
        order.setSetmealId(Integer.parseInt(setmealId));
        orderDao.add(order);
            //更新web页面预约信息
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,order);
    }

    @Override
    public Map getSuccessInfoByOrderId(Integer id) {
        return orderDao.getSuccessInfoByOrderId(id);
    }
}
