package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    JedisPool jedisPool;

    @Reference
    MemberService memberService;

    @RequestMapping("/check")
    public Result check(@RequestBody Map map, HttpServletResponse response){
        String tel = (String)map.get("tel");
        String validateCode = (String)map.get("validateCode");
        String codeFromRedis = jedisPool.getResource().get(tel + RedisMessageConstant.SENDTYPE_LOGIN);

        if (codeFromRedis==null || codeFromRedis.equalsIgnoreCase(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        Member member = memberService.getMemberByTel(tel);
        if (member==null){
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(tel);
            memberService.add(member);
        }

        //30天免登陆，使用cookie实现
        Cookie cookie = new Cookie("login_member_tel", tel);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);

        //登录成功
        return new Result(true ,MessageConstant.LOGIN_SUCCESS);
    }


}
