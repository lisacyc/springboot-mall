package com.chenlisa.springbootmall.dao;

import com.chenlisa.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer uid, Integer totalAmount);

    void createOrderItems(Integer oid, List<OrderItem> orderItemList);
}
