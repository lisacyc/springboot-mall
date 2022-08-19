package com.chenlisa.springbootmall.dao;

import com.chenlisa.springbootmall.dto.UserRegisterRequest;
import com.chenlisa.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer uid);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
