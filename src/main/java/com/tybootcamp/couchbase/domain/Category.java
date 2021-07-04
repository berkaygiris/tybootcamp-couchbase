package com.tybootcamp.couchbase.domain;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document
public class Category {
    @Field
    private String name;

    @Field
    private List<Product> products;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public void addProducts(List<Product> newProducts) {
        products.addAll(newProducts);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }
}
