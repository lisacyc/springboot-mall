package com.chenlisa.springbootmall.service.impl;

import com.chenlisa.springbootmall.dao.ProductDao;
import com.chenlisa.springbootmall.dto.ProductRequest;
import com.chenlisa.springbootmall.model.Product;
import com.chenlisa.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getById(Integer id) {
        return productDao.getProductById(id);
    }

    @Override
    public Integer create(ProductRequest data) {
        return productDao.create(data);
    }
}
