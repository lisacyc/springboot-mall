package com.chenlisa.springbootmall.service;

import com.chenlisa.springbootmall.dto.CreateOrderRequest;
import com.chenlisa.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer oid);

    Integer createOrder(Integer uid, CreateOrderRequest createOrderRequest);
}
