package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import com.atguigu.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    //上传
    @RequestMapping("/upload")
    public Result uploadExcel(@RequestParam("excelFile")MultipartFile excelFile){
        try {
            //1.使用POI解析文件 得到List<String[]> list
            //每条list为一个数组，每个数组为 "日期,可预约人数"
            List<String[]> listResult = POIUtils.readExcel(excelFile);
            //2.把List<String[]> list转成 List<OrderSetting> list
            List<OrderSetting> list = new ArrayList<>();
            for (String[] strings : listResult) {
                //注：Date使用util包，非sql包，sql包继承util包的Date类
                OrderSetting orderSetting = new OrderSetting(new Date(strings[0]),Integer.parseInt(strings[1]));
                list.add(orderSetting);
            }
            //3.调用业务 进行保存，保存的list为ordersetting集合
            orderSettingService.addBatch(list);

            return new Result(true, MessageConstant.UPLOAD_SUCCESS,listResult);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }
    }

    //查询某月份预约信息
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            //list的日期列表，每个日期包含map(日期，可预约数，已预约数)
            List<Map<String,Object>> list = orderSettingService.getMonthData(date);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
