package com.tybootcamp.couchbase.domain;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
  private List<Category> categories;

  public Seller(String name) {
    this.name = name;
    categories = List.of(
            new Category("uncategorized")
    );
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

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {this.categories = categories;}

  public List<Product> getProducts() {

    List<Product> products = null;
    boolean isProductsListInitiated = false;
    for (Category category: this.categories) {
      if(!isProductsListInitiated) {
        products = category.getProducts();
        isProductsListInitiated = true;
      } else {
        for (Product productOfCategories: category.getProducts()) {
          products.add(productOfCategories);
        }
      }
    }
    return products;

  }

  public void setProducts(List<Product> products) {
    this.categories = List.of(
            new Category("uncategorized", products)
    );
  }

  public int getCategoryIndex(String categoryName) {
    int categoryIndex = -1;
    int countForEveryIteration = 0;
    for (Category category: this.categories) {
      String existedCategoryName = category.getName();
      if(existedCategoryName.equalsIgnoreCase(categoryName)) {
        categoryIndex = countForEveryIteration;
        break;
      }
      countForEveryIteration++;
    }
    return categoryIndex;
  }

  public List<Product> getCategoryProductsByIndex(int categoryIndex) {
    return this.categories.get(categoryIndex).getProducts();
  }

  public void addProductToCategoryByIndex(int categoryIndex, Product product) {
    this.categories.get(categoryIndex).addProduct(product);
  }

  public void addProductsToCategoryByIndex(int categoryIndex, List<Product> products) {
    this.categories.get(categoryIndex).addProducts(products);
  }


  public void addCategory(String categoryName) {
    this.categories.add(new Category(categoryName));
  }
}
