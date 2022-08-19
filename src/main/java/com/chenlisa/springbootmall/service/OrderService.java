package com.chenlisa.springbootmall.service;

import com.chenlisa.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer uid, CreateOrderRequest createOrderRequest);
}
