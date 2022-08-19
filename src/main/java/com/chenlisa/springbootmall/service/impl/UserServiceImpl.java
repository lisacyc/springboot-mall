package com.chenlisa.springbootmall.service.impl;

import com.chenlisa.springbootmall.dao.UserDao;
import com.chenlisa.springbootmall.dto.UserLoginRequest;
import com.chenlisa.springbootmall.dto.UserRegisterRequest;
import com.chenlisa.springbootmall.model.User;
import com.chenlisa.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer uid) {
        return userDao.getUserById(uid);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查註冊的 email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            log.warn("該 email {} 已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 生成的密碼雜湊
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        // 檢查註冊的 email
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // 檢查 user 是否存在
        if (user == null) {
            log.warn("該 email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 生成的密碼雜湊
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // 比較密碼
        if (!user.getPassword().equals(hashedPassword)) {
            log.warn("email {} 密碼錯誤", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return user;
    }
}
