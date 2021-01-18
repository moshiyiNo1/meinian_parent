package com.atguigu.service.impl;

import com.atguigu.dao.MemberDao;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;

    @Override
    public Member getMemberByTel(String tel) {
        return memberDao.getMemberByTel(tel);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
