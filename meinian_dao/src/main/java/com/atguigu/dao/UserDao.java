package com.atguigu.dao;

import com.atguigu.pojo.User;

public interface UserDao {
    User loadUserByUsername(String username);
}
