package com.chenlisa.springbootmall.dao.impl;

import com.chenlisa.springbootmall.dao.OrderDao;
import com.chenlisa.springbootmall.model.Order;
import com.chenlisa.springbootmall.model.OrderItem;
import com.chenlisa.springbootmall.rowmapper.OrderItemRowMapper;
import com.chenlisa.springbootmall.rowmapper.OrderRowMapper;
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
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate sql;

    @Override
    public Order getOrderById(Integer oid) {
        String query = "select order_id, user_id, total_amount, created_date, last_modified_date " +
                "from `order` where order_id = :oid";

        Map<String, Object> map = new HashMap<>();
        map.put("oid", oid);

        List<Order> orderList = sql.query(query, new MapSqlParameterSource(map), new OrderRowMapper());

        return (orderList.size() > 0) ? orderList.get(0) : null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer oid) {
        String query = "select oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                "from order_item oi " +
                "left join product p on oi.product_id = p.product_id " +
                "where oi.order_id = :oid";

        Map<String, Object> map = new HashMap<>();
        map.put("oid", oid);

        List<OrderItem> itemList = sql.query(query, new MapSqlParameterSource(map), new OrderItemRowMapper());

        return itemList;
    }

    @Override
    public Integer createOrder(Integer uid, Integer totalAmount) {
        String query = "insert into `order` (user_id, total_amount, created_date, last_modified_date) " +
                "values (:uid, :totalAmount, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("totalAmount", totalAmount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        sql.update(query, new MapSqlParameterSource(map), keyHolder);

        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer oid, List<OrderItem> orderItemList) {
        String query = "insert into order_item(order_id, product_id, quantity, amount) " +
                "values (:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", oid);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }

        sql.batchUpdate(query, parameterSources);
    }
}
