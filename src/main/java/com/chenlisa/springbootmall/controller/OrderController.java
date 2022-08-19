package com.chenlisa.springbootmall.controller;

import com.chenlisa.springbootmall.dto.CreateOrderRequest;
import com.chenlisa.springbootmall.dto.OrderQueryParams;
import com.chenlisa.springbootmall.model.Order;
import com.chenlisa.springbootmall.service.OrderService;
import com.chenlisa.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("users/{uid}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer uid,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(uid);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        // 查詢訂單列表
        List<Order> orderList = orderService.getOrdersById(orderQueryParams);

        // 取得 Order 總數
        Integer total = orderService.countOrder(orderQueryParams);

        // 分頁
        Page<Order> page = new Page<>();
        page.setTotal(total);
        page.setLimit(limit);
        page.setOffset(offset);
        page.setResults(orderList);

        return ResponseEntity.status(200).body(page);
    }

    @PostMapping("users/{uid}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer uid,
                                         @RequestBody @Valid CreateOrderRequest orderRequest) {

        Integer oid = orderService.createOrder(uid, orderRequest);

        Order order = orderService.getOrderById(oid);

        return ResponseEntity.status(201).body(order);
    }
}
