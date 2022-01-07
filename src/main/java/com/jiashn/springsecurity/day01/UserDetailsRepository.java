package com.jiashn.springsecurity.day01;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.*;

/**
 * @Author: jiangjs
 * @Description: 自定义UserDetailsManager, 代理 {@link UserDetailsManager} 所有功能,
 *               UserDetailsManager：管理了用户的增删改查及是否过期等
 * @Date: 2022/1/6 11:24
 **/
public class UserDetailsRepository {

    private final Map<String, UserDetails> userMap = new HashMap<>();

    public void createUser(UserDetails userDetail){
        userMap.putIfAbsent(userDetail.getUsername(),userDetail);
    }

    public void updateUser(UserDetails userDetail){
        userMap.put(userDetail.getUsername(),userDetail);
    }

    public void deleteUser(String username) {
        userMap.remove(username);
    }

    public void changePassword(String oldPassword, String newPassword) {
        //获取当前用户认证信息（即登录信息）
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(currentUser)){
            throw new AccessDeniedException("当前没有登录用户认证信息");
        }
        UserDetails userDetail = userMap.get(currentUser.getName());
        if (Objects.isNull(userDetail)){
            throw new IllegalStateException("用户信息不存在");
        }
        //更新密码
    }

    public boolean userExists(String username) {
        return userMap.containsKey(username);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMap.get(username);
    }
}