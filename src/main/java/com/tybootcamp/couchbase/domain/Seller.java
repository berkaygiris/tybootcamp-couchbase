package com.tybootcamp.couchbase.domain;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Document
public class Seller {

  @Id
  @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
  private String id;

  @Field
  private String name;

  @Field
  private List<Product> products = new ArrayList<>();

  @Field
  private Map<String,List<Product>> categoryHashMap = new HashMap<>();

  public Seller(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public Map<String, List<Product>> getCategoryHashMap() {
    return categoryHashMap;
  }

  public void setCategoryHashMap(Map<String, List<Product>> categoryHashMap) {
    this.categoryHashMap = categoryHashMap;
  }
}