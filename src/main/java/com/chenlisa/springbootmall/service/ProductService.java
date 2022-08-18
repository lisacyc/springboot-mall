package com.chenlisa.springbootmall.service;

import com.chenlisa.springbootmall.dto.ProductRequest;
import com.chenlisa.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    Product getById(Integer id);

    Integer create(ProductRequest data);

    void update(Integer pid, ProductRequest data);

    void delete(Integer pid);
}
