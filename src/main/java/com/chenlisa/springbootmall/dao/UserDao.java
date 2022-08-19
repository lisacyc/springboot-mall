package com.chenlisa.springbootmall.dao;

import com.chenlisa.springbootmall.dto.UserRegisterRequest;
import com.chenlisa.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer uid);

    Integer register(UserRegisterRequest userRegisterRequest);
}
