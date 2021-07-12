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
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
  private final SellerRepository sellerRepository;

  public SellerService(SellerRepository sellerRepository) {
    this.sellerRepository = sellerRepository;
  }

  public Seller create(String name) {
    Optional<Seller> existSeller = this.sellerRepository.findByName(name);
    if (existSeller == null) {
      throw new RuntimeException(String.format("There is already a shop named as: ", name));
    } else {
      Seller seller = new Seller(name);
      return (Seller)this.sellerRepository.save(seller);
    }
  }

  public Seller findById(String id) {
    return (Seller)this.sellerRepository.findById(id).orElseThrow(() -> {
      return new RuntimeException(String.format("Seller not found with id: %s", id));
    });
  }

  public Seller findByName(String name) {
    return (Seller)this.sellerRepository.findByName(name).orElseThrow(() -> {
      return new RuntimeException(String.format("Seller not found with name : %s", name));
    });
  }

  public void addProductsToSeller(String sellerName, List<Product> products) {
    Seller seller = (Seller)this.sellerRepository.findByName(sellerName).orElseThrow(() -> {
      return new RuntimeException(String.format("Seller not found with name : %s", sellerName));
    });
    seller.setProducts(products);
    this.sellerRepository.save(seller);
  }

  public List<Product> getProductsByCategory(String sellerName, String category) {
    Seller seller = (Seller)this.sellerRepository.findByName(sellerName).orElseThrow(() -> {
      return new RuntimeException(String.format("Seller not found with name : %s", sellerName));
    });
    Optional<Category> category1 = seller.getCategories().stream().filter((c) -> {
      return c.getName() == category;
    }).findAny();
    if (category1.isPresent()) {
      return ((Category)category1.get()).getProducts();
    } else {
      throw new RuntimeException(String.format("Category not found with name : %s", category));
    }
  }

  public void AddCategory(String sellerName, Category category) {
    Seller seller = (Seller)this.sellerRepository.findByName(sellerName).orElseThrow(() -> {
      return new RuntimeException(String.format("Seller not found with name : %s", sellerName));
    });
    List<Category> categories = seller.getCategories();
    categories.add(category);
    seller.setCategories(categories);
    this.sellerRepository.save(seller);
  }
}
