package com.chenlisa.springbootmall.service.impl;

import com.chenlisa.springbootmall.dao.UserDao;
import com.chenlisa.springbootmall.dto.UserRegisterRequest;
import com.chenlisa.springbootmall.model.User;
import com.chenlisa.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer uid) {
        return userDao.getUserById(uid);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.register(userRegisterRequest);
    }
}
