package com.tybootcamp.couchbase.domain;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
public class Product {

  @Field
  private String name;

  @Field
  private Double price;

  @Field
  private String category = "uncategorized";

  public Product(){}

  public Product(String name, Double price) {
    this.name = name;
    this.price = price;
  }

  public Product(String name, Double price, String category) {
    this.name = name;
    this.price = price;
    this.category = category;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }


}
