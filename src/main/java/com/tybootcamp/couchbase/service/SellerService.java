package com.tybootcamp.couchbase.service;

import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import com.tybootcamp.couchbase.repository.SellerRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

  private final SellerRepository sellerRepository;

  public SellerService(SellerRepository sellerRepository) {
    this.sellerRepository = sellerRepository;
  }

  public Seller create(String name) {
    Seller seller = new Seller(name);
    return sellerRepository.save(seller);
  }

  public Seller findById(String id) {
    return sellerRepository.findById(id).orElseThrow(() -> new RuntimeException(
        String.format("Seller not found with id: %s", id))
    );
  }

  public Seller findByName(String name) {

    Seller temp = null;
    List<Seller> sellers = sellerRepository.findAll();

    for (Seller seller: sellers) {
      if(seller.getName().equalsIgnoreCase(name)){
        temp = seller;
      }else{
        throw new RuntimeException(
                String.format("Seller not found with name: %s", name));
      }
    }
    return temp;

  }

  public void addProductsToSeller(String sellerName, List<Product> products) {
    Seller seller = findByName(sellerName);
    seller.setProducts(products);
  }

  public List<Product> getProductsByCategory(String sellerName, String category) {
      Seller seller = findByName(sellerName);
      List<Product> products = seller.getProducts();
      List<Product> categoryProducts = new ArrayList<Product>();

      for (Product product: products){
        if(product.getCategory().equalsIgnoreCase(category)){
          categoryProducts.add(product);
        }
      }
      return categoryProducts;
  }
}
