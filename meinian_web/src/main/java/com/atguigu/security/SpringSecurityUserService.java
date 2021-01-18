package com.atguigu.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.pojo.Permission;
import com.atguigu.pojo.Role;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component//泛指组件，当组件不好归类时使用该注解
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username){
        //1.查询用户
        User user = userService.loadUserByUsername(username);//业务系统中的user实体类

        //4.构建权限集合
        Set<GrantedAuthority> authorities = new HashSet<>();

        //2.查询用户拥有的角色
        Set<Role> roles = user.getRoles();

        //3.查询用户拥有的角色所对应的权限
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        //5.返回用户明后才能，密码，权限集合
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
}
