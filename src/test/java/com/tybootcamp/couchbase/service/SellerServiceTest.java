//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tybootcamp.couchbase.service;

import com.tybootcamp.couchbase.domain.Category;
import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import com.tybootcamp.couchbase.repository.SellerRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SellerServiceTest {
  @Autowired
  private SellerService sellerService;
  @Autowired
  private SellerRepository sellerRepository;

  SellerServiceTest() {
  }

  @Test
  @Order(1)
  public void findById() {
    Seller createdSeller = this.sellerService.create("myShop");
    Seller foundSeller = this.sellerService.findById(createdSeller.getId());
    Assertions.assertEquals("myShop", foundSeller.getName());
  }

  @Test
  @Order(2)
  public void findByName() {
    this.sellerRepository.deleteByName("myShop");
    Seller myShop = this.sellerService.create("myShop");
    Seller foundSeller = this.sellerService.findByName("myShop");
    Assertions.assertEquals(myShop.getId(), foundSeller.getId());
  }

  @Test
  @Order(3)
  public void ensureSellerNameUniqueness() {
    this.sellerRepository.deleteByName("myShop");
    this.sellerService.create("myShop");
    RuntimeException exception = (RuntimeException)Assertions.assertThrows(RuntimeException.class, () -> {
      this.sellerService.create("myShop");
    });
    Assertions.assertEquals("There is already a shop named as: myshop", exception.getMessage());
  }

  @Test
  @Order(4)
  public void addProducts() {
    this.sellerRepository.deleteByName("myShop");
    this.sellerService.create("myShop");
    List<Product> products = List.of(new Product("glasses", 10.5D), new Product("shirt", 5.0D));
    this.sellerService.addProductsToSeller("myShop", products);
    Seller myShop = this.sellerService.findByName("myShop");
    Assertions.assertEquals(2, myShop.getProducts().size());
    Assertions.assertTrue(((List)myShop.getProducts().stream().map(Product::getName).collect(Collectors.toList())).containsAll(List.of("glasses", "shirt")));
  }

  @Test
  @Order(6)
  public void getProductsByCategory() {
    this.sellerRepository.deleteByName("myShop");
    Seller myShop = this.sellerService.create("myShop");
    List<Product> productsOfCategory1 = List.of(new Product("glasses", 10.5D), new Product("shirt", 5.0D));
    Category category1 = new Category("category1", productsOfCategory1);
    List<Product> productsOfCategory2 = List.of(new Product("keyboard", 100.0D), new Product("monitor", 500.0D));
    Category category2 = new Category("category2", productsOfCategory2);
    this.sellerService.AddCategory(myShop.getName(), category1);
    this.sellerService.AddCategory(myShop.getName(), category2);
    List<Product> productsByCategory1 = this.sellerService.getProductsByCategory("myShop", "category1");
    List<Product> productsByCategory2 = this.sellerService.getProductsByCategory("myShop", "category2");
    Assertions.assertEquals(2, productsByCategory1.size());
    Assertions.assertTrue(((List)productsByCategory1.stream().map(Product::getName).collect(Collectors.toList())).containsAll(List.of("glasses", "shirt")));
    Assertions.assertEquals(2, productsByCategory2.size());
    Assertions.assertTrue(((List)productsByCategory2.stream().map(Product::getName).collect(Collectors.toList())).containsAll(List.of("monitor", "keyboard")));
  }
}
