package com.peaceful.community.service;

import com.peaceful.community.dao.UserMapper;
import com.peaceful.community.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // 根据用户di查询用户的方法
    public User findUserById(int id){
        return userMapper.selectById(id);
    }


}
