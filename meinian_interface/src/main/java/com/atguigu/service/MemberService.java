package com.atguigu.service;

import com.atguigu.pojo.Member;

public interface MemberService {
    Member getMemberByTel(String tel);

    void add(Member member);
}
