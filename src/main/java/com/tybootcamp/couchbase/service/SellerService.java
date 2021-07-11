package com.tybootcamp.couchbase.service;

import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import com.tybootcamp.couchbase.repository.SellerRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class SellerService {

  private final SellerRepository sellerRepository;

  public SellerService(SellerRepository sellerRepository) {
    this.sellerRepository = sellerRepository;
  }


  public Seller create(String name) {
    if (sellerRepository.findByName(name) != null) {
      throw new RuntimeException("There is already a shop named as: " + name.toLowerCase());
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
    Seller s = sellerRepository.findByName(name);
    if (s == null)
      throw new RuntimeException("Implement me");
    return s;

  }

  public void addProductsToSeller(String name, List<Product> products) {
    try {
      Seller seller = sellerRepository.findByName(name);
      sellerRepository.deleteByName(seller.getName());
      seller.setProducts(products);
      sellerRepository.save(seller);
    } catch (Exception e) {
      throw new RuntimeException("There is no seller with this name.");
    }
  }

  public List<Product> getProductsByCategory(String sellerName, String category) {
    try {
      Seller seller = sellerRepository.findByName(sellerName);
      List<Product> products = seller.getProducts();
      List<Product> newProducts = products.stream().filter(x -> x.getCategory() == category).collect(Collectors.toList());
      return newProducts;
    } catch (Exception e) {
      throw new RuntimeException("There is no category named"+ category);
    }
  }
}