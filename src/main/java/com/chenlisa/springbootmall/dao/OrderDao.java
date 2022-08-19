package com.chenlisa.springbootmall.dao;

import com.chenlisa.springbootmall.model.Order;
import com.chenlisa.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Order getOrderById(Integer oid);

    List<OrderItem> getOrderItemsByOrderId(Integer oid);

    Integer createOrder(Integer uid, Integer totalAmount);

    void createOrderItems(Integer oid, List<OrderItem> orderItemList);
}
