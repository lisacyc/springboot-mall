package com.chenlisa.springbootmall.constant;

public enum ProductCategory {

    FOOD("食物"),
    CAR("車子"),
    BOOK("書本");

    public final String label;

    ProductCategory(String label) {
        this.label = label;
    }
}
