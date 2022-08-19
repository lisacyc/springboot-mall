package com.chenlisa.springbootmall.controller;

import com.chenlisa.springbootmall.dto.CreateOrderRequest;
import com.chenlisa.springbootmall.model.Order;
import com.chenlisa.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("users/{uid}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer uid,
                                         @RequestBody @Valid CreateOrderRequest orderRequest) {

        Integer oid = orderService.createOrder(uid, orderRequest);

        Order order = orderService.getOrderById(oid);

        return ResponseEntity.status(201).body(order);
    }
}
