package com.chenlisa.springbootmall.dao;

import com.chenlisa.springbootmall.dto.ProductRequest;
import com.chenlisa.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer id);

    Integer create(ProductRequest data);

    void update(Integer pid, ProductRequest data);
}
