package com.chenlisa.springbootmall.service;

import com.chenlisa.springbootmall.dto.CreateOrderRequest;
import com.chenlisa.springbootmall.dto.OrderQueryParams;
import com.chenlisa.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer oid);

    List<Order> getOrdersById(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer uid, CreateOrderRequest createOrderRequest);
}
