package com.chenlisa.springbootmall.dao.impl;

import com.chenlisa.springbootmall.dao.ProductDao;
import com.chenlisa.springbootmall.model.Product;
import com.chenlisa.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate sql;

    @Override
    public Product getProductById(Integer id) {
        String query = "select product_id, product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date " +
                "from product " +
                "where product_id = :pid";

        Map<String, Object> map = new HashMap<>();
        map.put("pid", id);
        List<Product> productList = sql.query(query, map, new ProductRowMapper());

        return (productList.size() > 0) ? productList.get(0) : null;
    }
}
