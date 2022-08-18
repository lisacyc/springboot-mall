package com.chenlisa.springbootmall.dao;

import com.chenlisa.springbootmall.dto.ProductRequest;
import com.chenlisa.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();

    Product getProductById(Integer id);

    Integer create(ProductRequest data);

    void update(Integer pid, ProductRequest data);

    void delete(Integer pid);
}
