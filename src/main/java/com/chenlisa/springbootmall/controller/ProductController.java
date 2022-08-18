package com.chenlisa.springbootmall.controller;

import com.chenlisa.springbootmall.dto.ProductRequest;
import com.chenlisa.springbootmall.model.Product;
import com.chenlisa.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{pid}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer pid) {
        Product product = productService.getById(pid);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.create(productRequest);
        Product product = productService.getById(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/products/{pid}")
    public ResponseEntity<Product> createProduct(@PathVariable Integer pid,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        // 檢查商品是否存在
        Product product = productService.getById(pid);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.update(pid, productRequest);
        Product updatedProduct = productService.getById(pid);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{pid}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer pid) {
        productService.delete(pid);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
