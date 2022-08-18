package com.chenlisa.springbootmall.service.impl;

import com.chenlisa.springbootmall.dao.ProductDao;
import com.chenlisa.springbootmall.dto.ProductQueryParams;
import com.chenlisa.springbootmall.dto.ProductRequest;
import com.chenlisa.springbootmall.model.Product;
import com.chenlisa.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        return productDao.countProduct(productQueryParams);
    }

    @Override
    public Product getById(Integer id) {
        return productDao.getProductById(id);
    }

    @Override
    public Integer create(ProductRequest data) {
        return productDao.create(data);
    }

    @Override
    public void update(Integer pid, ProductRequest data) {
        productDao.update(pid, data);
    }

    @Override
    public void delete(Integer pid) {
        productDao.delete(pid);
    }
}
