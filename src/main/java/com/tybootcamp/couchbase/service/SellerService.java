package com.tybootcamp.couchbase.service;

import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import com.tybootcamp.couchbase.repository.SellerRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import javax.swing.plaf.OptionPaneUI;

@Service
public class SellerService {

  private final SellerRepository sellerRepository;

  public SellerService(SellerRepository sellerRepository) {
    this.sellerRepository = sellerRepository;
  }

  public Seller create(String name) throws RuntimeException {
    Optional<Seller> optionalSeller = sellerRepository.findByName(name);
    if (optionalSeller.isPresent()) {
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
    Optional<Seller> optionalSeller = sellerRepository.findByName(name);
    return optionalSeller
            .orElseThrow(() -> new RuntimeException("seller not found"));
  }

  public void addProductsToSeller(String sellerName, List<Product> products) {
    Seller seller = findByName(sellerName);
    seller.setProducts(products);
    sellerRepository.save(seller);
  }

  public List<Product> getProductsByCategory(String sellerName, String category) {
    Seller seller = findByName(sellerName);
    List<Product> products= seller.getProducts();
    return products.stream()
            .filter(product -> product.getCategory()
                    .contains(category)).collect(Collectors.toList());

  }
}
