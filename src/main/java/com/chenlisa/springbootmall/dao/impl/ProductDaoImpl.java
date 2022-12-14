package com.chenlisa.springbootmall.dao.impl;

import com.chenlisa.springbootmall.dao.ProductDao;
import com.chenlisa.springbootmall.dto.ProductQueryParams;
import com.chenlisa.springbootmall.dto.ProductRequest;
import com.chenlisa.springbootmall.model.Product;
import com.chenlisa.springbootmall.rowmapper.ProductRowMapper;
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
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate sql;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String query = "SELECT product_id, product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date " +
                "FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // 查詢
        query = addFilteringSql(query, map, productQueryParams);

        // 排序
        query += " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        // 分頁
        query += " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        List<Product> productList = sql.query(query, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String query = "SELECT COUNT(*) FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // 查詢
        query = addFilteringSql(query, map, productQueryParams);

        Integer totalCount = sql.queryForObject(query, map, Integer.class);

        return totalCount;
    }

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

    @Override
    public Integer create(ProductRequest data) {
        String query = "INSERT INTO product (product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date) VALUES" +
                " (:productName, :category, :imageUrl, :price, :stock, :description, " +
                ":createdDate, :lastModifiedDate);";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", data.getProductName());
        map.put("category", data.getCategory().toString());
        map.put("imageUrl", data.getImageUrl());
        map.put("price", data.getPrice());
        map.put("stock", data.getStock());
        map.put("description", data.getDescription());

        Date date = new Date();
        map.put("createdDate", date);
        map.put("lastModifiedDate", date);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        sql.update(query, new MapSqlParameterSource(map), keyHolder);

        int pid = keyHolder.getKey().intValue();

        return pid;
    }

    @Override
    public void update(Integer pid, ProductRequest data) {
        String query = "UPDATE product SET product_name = :productName, category = :category, " +
                "image_url = :imageUrl, price = :price, stock = :stock, description = :description, " +
                "last_modified_date = :lastModifiedDate WHERE product_id = :id";

        Map<String, Object> map = new HashMap<>();
        map.put("id", pid);
        map.put("productName", data.getProductName());
        map.put("category", data.getCategory().toString());
        map.put("imageUrl", data.getImageUrl());
        map.put("price", data.getPrice());
        map.put("stock", data.getStock());
        map.put("description", data.getDescription());
        map.put("lastModifiedDate", new Date());

        sql.update(query, map);
    }

    @Override
    public void updateStock(List<Object> updateStockList) {
        String query = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate" +
                " WHERE product_id = :id";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[updateStockList.size()];

        for (int i = 0; i < updateStockList.size(); i++) {
            Map<String, Object> data = (Map<String, Object>) updateStockList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("id", data.get("pid"));
            parameterSources[i].addValue("stock", data.get("newStock"));
            parameterSources[i].addValue("lastModifiedDate", new Date());
        }

        sql.batchUpdate(query, parameterSources);
    }

    @Override
    public void delete(Integer pid) {
        String query = "DELETE FROM product WHERE product_id = :id";

        Map<String, Object> map = new HashMap<>();
        map.put("id", pid);

        sql.update(query, map);
    }

    private String addFilteringSql(String query, Map<String, Object> map, ProductQueryParams productQueryParams) {
        if (productQueryParams.getCategory() != null) {
            query += " AND category = :category";
            map.put("category", productQueryParams.getCategory().name());
        }

        if (productQueryParams.getSearch() != null) {
            query += " AND product_name LIKE :productName";
            map.put("productName", "%" + productQueryParams.getSearch() + "%");
        }

        return query;
    }
}
