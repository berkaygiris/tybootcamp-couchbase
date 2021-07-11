package com.tybootcamp.couchbase.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.Collection;
import java.util.Set;

@Document
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
  private String id;

  @Field
  private String name;

  @Field
  private Double price;

  @Field
  private Set<String> categories;

  public Product(String name, Double price) {
    this.name = name;
    this.price = price;
  }

  public Product withCategories(Collection<String> categories) {
    this.setCategories((Set<String>) categories);
    return this;
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

  public void addCategory(String category) {
    categories.add(category);
  }

  public void removeCategory(String category) {
    categories.remove(category);
  }

  public Set<String> getCategories() {
    return categories;
  }

  public void setCategories(Set<String> categories) {
    this.categories = categories;
  }
}
