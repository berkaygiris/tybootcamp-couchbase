package com.tybootcamp.couchbase.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import com.tybootcamp.couchbase.repository.SellerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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


  /**
   * Fetches a document by it's id. Therefore it does not need any additional secondary index.
   */
  @Test
  @Order(1)
  public void findById() {
    //Given
    sellerRepository.deleteByName("myShop");
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
    sellerRepository.deleteByName("myShop"); //to be make sure of uniqueness
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
    sellerRepository.deleteByName("myShop"); //clean up first
    sellerService.create("myShop"); //first time

    //When
    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        sellerService.create("myShop") //second time
    );

    //Then
    assertEquals("There is already a shop named as: myShop", exception.getMessage());
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
    sellerRepository.deleteByName("myShop"); //clean up first
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

  @Test
  @Order(6)
  public void getProductsByCategory() {
    //Given
    sellerRepository.deleteByName("myShop"); //clean up first
    Seller myShop = sellerService.create("myShop");

    //glasses for category1
    Product glasses=new Product("glasses",3.3);
    glasses.setCategories(Arrays.asList("category1"));
    //shirt for category1
    Product shirt=new Product("shirt",4.2);
    shirt.setCategories(Arrays.asList("category1"));
    //monitor
    Product monitor=new Product("monitor",5.5);
    monitor.setCategories(Arrays.asList("category2"));
    //keyboard
    Product keyboard=new Product("keyboard",6.6);
    keyboard.setCategories(Arrays.asList("category2"));
    //add products to seller
    sellerService.addProductsToSeller("myShop",Arrays.asList(glasses,shirt,monitor,keyboard));
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