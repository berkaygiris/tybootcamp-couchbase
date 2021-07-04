package com.tybootcamp.couchbase.service;

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

  public Seller create(String name) {
    Seller s = sellerRepository.findByName(name).get();
    if (s == null){
      Seller seller = new Seller(name);
      return sellerRepository.save(seller);
    }
    else{
        throw new RuntimeException(
                String.format("There is already a shop named as: %s", name.toLowerCase()));
    }
  }

  public Seller findById(String id) {
    return sellerRepository.findById(id).orElseThrow(() -> new RuntimeException(
        String.format("Seller not found with id: %s", id))
    );
  }

  public Seller findByName(String name) {
    return sellerRepository.findByName(name).orElseThrow(()-> new RuntimeException(
            String.format("Seller not found with name: %s",name)
    ));
  }

  public void addProductsToSeller(String sellerName, List<Product> products) {
    Seller s = findByName(sellerName);
    s.setProducts(products);
    sellerRepository.save(s);
  }

  public List<Product> getProductsByCategory(String sellerName, String category) {

/*

    Seller s = findByName(sellerName);

    List<Product> productList = new ArrayList<>();
    List<Product> sellerProductList = s.getProducts();
    for (Product p : sellerProductList)
    {
      if (p.getCategory().equals(category)){
        productList.add(p);
      }
    }

    return productList;*/


    Seller seller = findByName(sellerName);

    return seller.getCategoryHashMap().get(category);


  }
}
