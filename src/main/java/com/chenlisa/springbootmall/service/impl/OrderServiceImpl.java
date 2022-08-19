package com.chenlisa.springbootmall.service.impl;

import com.chenlisa.springbootmall.dao.OrderDao;
import com.chenlisa.springbootmall.dao.ProductDao;
import com.chenlisa.springbootmall.dao.UserDao;
import com.chenlisa.springbootmall.dto.BuyItem;
import com.chenlisa.springbootmall.dto.CreateOrderRequest;
import com.chenlisa.springbootmall.dto.OrderQueryParams;
import com.chenlisa.springbootmall.model.Order;
import com.chenlisa.springbootmall.model.OrderItem;
import com.chenlisa.springbootmall.model.Product;
import com.chenlisa.springbootmall.model.User;
import com.chenlisa.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public Order getOrderById(Integer oid) {
        Order order = orderDao.getOrderById(oid);

        List<OrderItem> itemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
        order.setOrderItemList(itemList);

        return order;
    }

    @Override
    public List<Order> getOrdersById(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        // 取得 order 詳細資訊
        for (Order order : orderList) {
            List<OrderItem> itemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItemList(itemList);
        }

        return orderList;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer uid, CreateOrderRequest createOrderRequest) {
        // 檢查 user 是否存在
        User user = userDao.getUserById(uid);
        if (user == null) {
            log.warn("該 userId {} 不存在", uid);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;

        List<OrderItem> orderItemList = new ArrayList<>();
        List<Object> updateStockList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查商品是否存在、庫存是否足夠
            if (product == null) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存不足，無法購買。剩餘庫存 {}，欲購買數量 {}", buyItem.getProductId(),
                        product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 待更新商品庫存資訊
            Map<String, Object> productStockList = new HashMap<>();
            productStockList.put("pid", product.getProductId());
            productStockList.put("newStock", (product.getStock() - buyItem.getQuantity()));
            updateStockList.add(productStockList);

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

        // 扣除商品庫存
        productDao.updateStock(updateStockList);

        // 創建訂單
        Integer oid = orderDao.createOrder(uid, totalAmount);

        orderDao.createOrderItems(oid, orderItemList);

        return oid;
    }
}
