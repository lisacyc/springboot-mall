package com.chenlisa.springbootmall.dao;

import com.chenlisa.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer id);
}
