package com.atguigu.dao;

import java.util.List;

public interface RoleDao {
    List findRolesByUserId(Integer userId);
}
