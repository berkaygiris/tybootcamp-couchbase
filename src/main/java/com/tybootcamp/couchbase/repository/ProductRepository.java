package com.tybootcamp.couchbase.repository;

import com.tybootcamp.couchbase.domain.Product;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CouchbaseRepository<Product, String> {
  void deleteByName(String name);
}
