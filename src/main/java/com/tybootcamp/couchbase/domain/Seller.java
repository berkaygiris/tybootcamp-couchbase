package com.tybootcamp.couchbase.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document
public class Seller {

  @Id
  @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
  private String id;

  @Field
  private String name;

  @Field
  private List<Product> products;

  @Field
  private Map<String, Category> categories = new HashMap<>();

  public Seller(String name) {
    this.name = name;
  }

  public void addCategory(String categoryName) {
    categories.put(categoryName, new Category(categoryName));
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

  public Map<String, Category> getCategories() {
    return categories;
  }
}
