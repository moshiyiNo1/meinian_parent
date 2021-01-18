package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.utils.SMSUtils;
import com.atguigu.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/ValidateCode")
public class ValidateCodeController {
    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/send4Login")
    public Result send4Login(String tel){
        try {
            //获取手机号
            //生成验证码
            String s = ValidateCodeUtils.generateValidateCode(4).toString();
            //使用第三方服务发送验证码
            SMSUtils.sendShortMessage(tel,s);
            //生成的验证码存入redis(存5分钟)
            jedisPool.getResource().setex(tel+ RedisMessageConstant.SENDTYPE_LOGIN,300,s);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/telephoneCode")
    public Result telephoneCode(String tel){
        try {
            //获取手机号
            //生成验证码
            String s = ValidateCodeUtils.generateValidateCode(4).toString();
            //使用第三方服务发送验证码
            SMSUtils.sendShortMessage(tel,s);
            //生成的验证码存入redis(存5分钟)
            jedisPool.getResource().setex(tel+ RedisMessageConstant.SENDTYPE_ORDER,300,s);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
