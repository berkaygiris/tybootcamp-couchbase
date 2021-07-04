package com.tybootcamp.couchbase.service;

import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import com.tybootcamp.couchbase.repository.SellerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SellerServiceTest {

  @Autowired
  private SellerService sellerService;
  @Autowired
  private SellerRepository sellerRepository;

  @AfterEach
  public void clearSellers() {
    sellerRepository.deleteAll();
  }

  /**
   * Fetches a document by it's id. Therefore it does not need any additional secondary index.
   */
  @Test
  @Order(1)
  public void findById() {
    //Given
    Seller createdSeller = sellerService.create("myShop");

    //When
    Seller foundSeller = sellerService.findById(createdSeller.getId());

    //Then
    assertEquals("myShop", foundSeller.getName());
  }

  /**
   * This test does operations with querying the 'name' field. For this example we need a secondary
   * index to query.
   * <p>
   * You can create the index by 'CREATE INDEX idx_sellers_name on sellers(name)'.
   */
  @Test
  @Order(2)
  public void findByName() {
    //Given
    Seller myShop = sellerService.create("myShop");

    //When
    Seller foundSeller = sellerService.findByName("myShop");

    //Then
    assertEquals(myShop.getId(), foundSeller.getId());
  }

  /**
   * Let's say that we will only allow sellers with unique names.
   * <p>
   * Throw an exception when create method is called twice with the same argument. You must also add
   * a meaningful error message as "There is already a shop named as: %s"
   */
  @Test
  @Order(3)
  public void ensureSellerNameUniqueness() {
    //Given
    sellerService.create("myShop"); //first time

    //When
    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        sellerService.create("myShop") //second time
    );

    //Then
    assertEquals("There is already a shop named as: myshop", exception.getMessage());
  }

  /**
   * Make myShop sell some items.
   * <p>
   * Remember that on creation, the seller does not sell any items.
   */
  @Test
  @Order(4)
  public void addProducts() {
    //Given
    sellerService.create("myShop");
    List<Product> products = List.of(
        new Product("glasses", 10.5),
        new Product("shirt", 5.0)
    );

    //When
    sellerService.addProductsToSeller("myShop", products);

    //Then
    Seller myShop = sellerService.findByName("myShop");
    assertEquals(2, myShop.getProducts().size());
    assertTrue(myShop.getProducts().stream()
        .map(Product::getName)
        .collect(Collectors.toList())
        .containsAll(List.of("glasses", "shirt")));
  }

  /**
   * Implement a category filter for seller's products.
   * <p>
   * In this example we want to query the products by their categories. According to When and Then
   * statements. The following result is expected.
   * <p>
   * category1 -> ["glasses", "shirt"] category2 -> ["keyboard", "monitor"]
   */
  @Test
  @Order(6)
  public void getProductsByCategory() {
    //Given
    Seller myShop = sellerService.create("myShop");

    sellerService.addCategoryToSeller("myShop", "category1");
    sellerService.addCategoryToSeller("myShop", "category2");
    sellerService.addProductsToSellerCategory("myShop", "category1",
            List.of(new Product("glasses", 10.05), new Product("shirt", 5.0)));
    sellerService.addProductsToSellerCategory("myShop", "category2",
            List.of(new Product("monitor", 53.21), new Product("keyboard", 32.05)));
    // TODO: Add products ["glasses", "shirt", "monitor", "keyboard"] to myShop here
    // You need to find a way to query them with their categories.
    // Think about the performance on scale

    //When
    List<Product> productsByCategory1 = sellerService.getProductsByCategory("myShop", "category1");
    List<Product> productsByCategory2 = sellerService.getProductsByCategory("myShop", "category2");

    //Then
    assertEquals(2, productsByCategory1.size());
    assertTrue(productsByCategory1.stream()
        .map(Product::getName)
        .collect(Collectors.toList())
        .containsAll(List.of("glasses", "shirt")));
    assertEquals(2, productsByCategory2.size());
    assertTrue(productsByCategory2.stream()
        .map(Product::getName)
        .collect(Collectors.toList())
        .containsAll(List.of("monitor", "keyboard")));
  }
}