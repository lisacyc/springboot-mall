package com.chenlisa.springbootmall.service;

import com.chenlisa.springbootmall.dto.UserRegisterRequest;
import com.chenlisa.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer uid);

    Integer register(UserRegisterRequest userRegisterRequest);
}
