package com.tybootcamp.couchbase.domain;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.List;

@Document
public class Category {

    @Field
    private String name;

    @Field
    private List<Product> products;

    public Category(String name) {
        this.setName(name);
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        if (this.products != null) {
            products.add(product);
        } else {
            this.products = List.of(product);
        }
    }

    public void addProducts(List<Product> products) {
        if (this.products != null) {
            products.addAll(products);
        } else {
            this.products = products;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
