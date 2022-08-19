package com.chenlisa.springbootmall.dao.impl;

import com.chenlisa.springbootmall.dao.UserDao;
import com.chenlisa.springbootmall.dto.UserRegisterRequest;
import com.chenlisa.springbootmall.model.User;
import com.chenlisa.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate sql;

    @Override
    public User getUserById(Integer uid) {
        String query = "select user_id, email, password, created_date, last_modified_date " +
                "from user where user_id = :id";

        Map<String, Object> map = new HashMap<>();
        map.put("id", uid);

        List<User> user = sql.query(query, map, new UserRowMapper());

        return (user.size() > 0) ? user.get(0) : null;
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        String query = "INSERT INTO user (email, password, created_date, last_modified_date) " +
                "VALUES (:email, :password, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        sql.update(query, new MapSqlParameterSource(map), keyHolder);

        int id = keyHolder.getKey().intValue();

        return id;
    }
}
