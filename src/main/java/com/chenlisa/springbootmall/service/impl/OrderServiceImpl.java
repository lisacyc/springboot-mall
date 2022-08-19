package com.chenlisa.springbootmall.service.impl;

import com.chenlisa.springbootmall.dao.OrderDao;
import com.chenlisa.springbootmall.dao.ProductDao;
import com.chenlisa.springbootmall.dto.BuyItem;
import com.chenlisa.springbootmall.dto.CreateOrderRequest;
import com.chenlisa.springbootmall.model.Order;
import com.chenlisa.springbootmall.model.OrderItem;
import com.chenlisa.springbootmall.model.Product;
import com.chenlisa.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Integer oid) {
        Order order = orderDao.getOrderById(oid);

        List<OrderItem> itemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
        order.setOrderItemList(itemList);

        return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer uid, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;

        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());
            if (product != null) {
                // 計算總價錢
                int amount = product.getPrice() * buyItem.getQuantity();
                totalAmount += amount;

                // 轉換 BuyItem to OrderItem
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(buyItem.getProductId());
                orderItem.setQuantity(buyItem.getQuantity());
                orderItem.setAmount(amount);
                orderItemList.add(orderItem);
            }
        }

        // 創建訂單
        Integer oid = orderDao.createOrder(uid, totalAmount);

        orderDao.createOrderItems(oid, orderItemList);

        return oid;
    }
}
