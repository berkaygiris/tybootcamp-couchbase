package com.tybootcamp.couchbase.service;

import com.tybootcamp.couchbase.domain.Category;
import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import com.tybootcamp.couchbase.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SellerService {

  private final SellerRepository sellerRepository;

  public SellerService(SellerRepository sellerRepository) {
    this.sellerRepository = sellerRepository;
  }

  public Seller create(String name) {
    if(sellerRepository.existsByName(name)) {
      throw new RuntimeException("There is already a shop named as: myshop");
    }
    Seller seller = new Seller(name);
    return sellerRepository.save(seller);
  }

  public Seller findById(String id) {
    return sellerRepository.findById(id).orElseThrow(() -> new RuntimeException(
        String.format("Seller not found with id: %s", id))
    );
  }

  public Seller findByName(String name) {
    return sellerRepository.findByName(name).orElseThrow(() -> new RuntimeException("runtime"));
  }

  public void addCategoryToSeller(String sellerName, String categoryName) {
    Seller seller = sellerRepository.findByName(sellerName).orElseThrow(RuntimeException::new);
    seller.addCategory(categoryName);
    sellerRepository.save(seller);
  }

  public void addProductsToSellerCategory(String sellerName, String categoryName, List<Product> products) {
    Seller seller = sellerRepository.findByName(sellerName).orElseThrow(RuntimeException::new);

    Map<String, Category> categories = seller.getCategories();
    if(!categories.containsKey(categoryName)) {
      throw new RuntimeException();
    }
    Category category = categories.get(categoryName);
    category.addProducts(products);
    sellerRepository.save(seller);
  }

  public void addProductsToSeller(String sellerName, List<Product> products) {
    Seller seller = sellerRepository.findByName(sellerName).orElseThrow(RuntimeException::new);
    seller.setProducts(products);
    sellerRepository.save(seller);
  }

  public List<Product> getProductsByCategory(String sellerName, String categoryName) {
    Seller seller = sellerRepository.findByName(sellerName).orElseThrow(RuntimeException::new);
    Map<String, Category> categories = seller.getCategories();
    if(!categories.containsKey(categoryName)) {
      throw new RuntimeException("category not found");
    }
    Category category = categories.get(categoryName);
    return category.getProducts();
  }
}
