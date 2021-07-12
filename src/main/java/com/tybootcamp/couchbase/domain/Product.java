//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
  private String categoryName;

  public Product(String name, Double price) {
    this.name = name;
    this.price = price;
  }

  public Product(String name, Double price, String categoryName) {
    this.name = name;
    this.price = price;
    this.categoryName = categoryName;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
