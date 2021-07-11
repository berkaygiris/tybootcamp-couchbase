package com.tybootcamp.couchbase.service;

import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import com.tybootcamp.couchbase.repository.SellerRepository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Seller create(String name) {

        if (!sellerRepository.existsByName(name)) {
            Seller seller = new Seller(name);
            return sellerRepository.save(seller);
        } else
            throw new RuntimeException(String.format("There is already a shop named as: %s", name));
    }

    public Seller findById(String id) {
        return sellerRepository.findById(id).orElseThrow(() -> new RuntimeException(
                String.format("Seller not found with id: %s", id))
        );
    }

    public Seller findByName(String name) {
        Optional<Seller> optionalSeller = sellerRepository.findByName(name);
        return optionalSeller.orElseThrow(() -> new RuntimeException(
                String.format("Seller not found with name: %s", name))
        );
    }

    public void addProductsToSeller(String sellerName, List<Product> products) {
        Seller seller = this.findByName(sellerName);
        seller.setProducts(products);
        sellerRepository.save(seller);
    }

    public List<Product> getProductsByCategory(String sellerName, String category) {
        //TODO: Not yet implemented
        throw new RuntimeException("Implement me");
    }
}
