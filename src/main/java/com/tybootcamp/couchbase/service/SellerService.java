package com.tybootcamp.couchbase.service;

import com.tybootcamp.couchbase.domain.Category;
import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import com.tybootcamp.couchbase.repository.SellerRepository;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class SellerService {

  private final SellerRepository sellerRepository;

  public SellerService(SellerRepository sellerRepository) {
    this.sellerRepository = sellerRepository;
  }

  public Seller create(String name) throws RuntimeException {
    Optional<Seller> optionalSeller = sellerRepository.findByName(name);
    if(!optionalSeller.isEmpty()) {
      throw new RuntimeException("There is already a shop named as: " + name.toLowerCase(Locale.ROOT));
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
    Optional<Seller> optionalSeller = sellerRepository.findByName(name);
    Seller seller = optionalSeller.orElseThrow(() -> new RuntimeException("Seller not found"));
    return seller;
  }

  public void addProductsToSeller(String sellerName, List<Product> products) {
    Optional<Seller> optionalSeller = sellerRepository.findByName(sellerName);
    Seller seller = optionalSeller.orElseThrow(() -> new RuntimeException("Seller not found"));
    List<Product> productsOfSeller = seller.getProducts();
    if(productsOfSeller == null) {
      productsOfSeller = products;
    } else {
      for (Product product: products) {
        productsOfSeller.add(product);
      }
    }
    seller.setProducts(productsOfSeller);
    sellerRepository.save(seller);

  }

  public void addProductsToSeller(String sellerName, List<Product> products, String categoryName) {
    int categoryIndexNotExist = -1;
    List<Product> productsOfSeller = null;

    Optional<Seller> optionalSeller = sellerRepository.findByName(sellerName);
    Seller seller = optionalSeller.orElseThrow(() -> new RuntimeException("Seller not found"));

    int categoryIndex = seller.getCategoryIndex(categoryName);

    if(categoryIndex == categoryIndexNotExist) {
      seller.addCategory(categoryName);
      categoryIndex = seller.getCategoryIndex(categoryName);
      seller.addProductsToCategoryByIndex(categoryIndex,products);
    } else {
      productsOfSeller = seller.getCategoryProductsByIndex(categoryIndex);
      for (Product product: products) {
        productsOfSeller.add(product);
      }
      seller.addProductsToCategoryByIndex(categoryIndex,productsOfSeller);
    }


    sellerRepository.save(seller);

  }

  public List<Product> getProductsByCategory(String sellerName, String category) {
    int categoryIndexNotExist = -1;
    List<Product> products = null;
    Optional<Seller> optionalSeller = sellerRepository.findByName(sellerName);
    Seller seller = optionalSeller.orElseThrow(() -> new RuntimeException("Seller not found"));
    int categoryIndex = seller.getCategoryIndex(category);
    if(categoryIndex != categoryIndexNotExist) {
      products = seller.getCategoryProductsByIndex(categoryIndex);
    }
    return products;
  }

}
