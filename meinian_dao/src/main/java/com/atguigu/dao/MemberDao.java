package com.atguigu.dao;

import com.atguigu.pojo.Member;

public interface MemberDao {
    Member getMemberByTel(String tel);

    void add(Member member);
}
