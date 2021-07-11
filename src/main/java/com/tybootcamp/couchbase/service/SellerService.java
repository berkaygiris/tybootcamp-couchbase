package com.tybootcamp.couchbase.service;

import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import com.tybootcamp.couchbase.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {

  private final SellerRepository sellerRepository;

  public SellerService(SellerRepository sellerRepository) {
    this.sellerRepository = sellerRepository;
  }

  public Seller create(String name) {
    boolean sellerExists = sellerRepository.existsByName(name);
    if (sellerExists)
      throw new RuntimeException(String.format("There is already a shop named as: %s", name));

    Seller seller = Seller.fromName(name);
    return sellerRepository.save(seller);
  }

  public Seller findById(String id) {
    return sellerRepository.findById(id).orElseThrow(() -> new RuntimeException(
        String.format("Seller not found with id: %s", id))
    );
  }

  public Seller findByName(String name) {
    return sellerRepository.findByName(name).orElseThrow(() -> new RuntimeException(
        String.format("Seller not found with name: %s", name)
    ));
  }

  public void addProductsToSeller(String sellerName, List<Product> products) {
    Seller seller = this.findByName(sellerName);
    seller.getProducts().addAll(products);
    sellerRepository.save(seller);
  }

  public List<Product> getProductsByCategory(String sellerName, String category) {
    return this.findByName(sellerName).getProducts()
        .stream()
        .filter(product -> product.getCategories().contains(category))
        .collect(Collectors.toList());
  }
}
