package com.atguigu.service.impl;

import com.atguigu.dao.UserDao;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl  implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User loadUserByUsername(String username) {
        return userDao.loadUserByUsername(username);
    }
}
