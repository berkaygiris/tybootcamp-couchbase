package com.tybootcamp.couchbase.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.PersistenceConstructor;
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
        this.name = name;
    }

    @PersistenceConstructor
    public Category(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        if(this.products != null) {
            this.products.add(product);
        } else {
            this.products =List.of(product);
        }
    }

    public void addProducts(List<Product> products) {
        if(this.products != null) {
            this.products.addAll(products);
        } else {
            this.products = products;
        }
    }
}
