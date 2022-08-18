package com.chenlisa.springbootmall.service;

import com.chenlisa.springbootmall.dto.ProductRequest;
import com.chenlisa.springbootmall.model.Product;

public interface ProductService {
    Product getById(Integer id);

    Integer create(ProductRequest data);
}
