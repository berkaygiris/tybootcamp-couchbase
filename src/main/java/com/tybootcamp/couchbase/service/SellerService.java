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
    Seller s = sellerRepository.findByName(name);
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

    Seller seller = sellerRepository.findByName(name);
    if (seller == null){
      throw new RuntimeException(String.format("Seller not found with name: %s",name));
    }
    return seller;
  }

  public void addProductsToSeller(String sellerName, List<Product> products) {

    Seller seller = findByName(sellerName);

    List<Product> sellerProductList = seller.getProducts();
    for (Product p : products){
      if (!sellerProductList.contains(p)){
        sellerProductList.add(p);
      }
    }
    seller.setProducts(sellerProductList);


    Map<String,List<Product>> sellerCategoryHashMap = seller.getCategoryHashMap();
    for(Product p : products){
      if (sellerCategoryHashMap.containsKey(p.getCategory())){
        List<Product> pList = sellerCategoryHashMap.get(p.getCategory());
        pList.add(p);
        sellerCategoryHashMap.put(p.getCategory(),pList);
      }
      else{
        List<Product> pList = new ArrayList<>();
        pList.add(p);
        sellerCategoryHashMap.put(p.getCategory(),pList);
      }
    }

    seller.setCategoryHashMap(sellerCategoryHashMap);

    sellerRepository.save(seller);
  }

  public List<Product> getProductsByCategory(String sellerName, String category) {

    Seller seller = findByName(sellerName);

    return seller.getCategoryHashMap().get(category);


  }
}
