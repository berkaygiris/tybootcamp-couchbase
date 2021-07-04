package com.tybootcamp.couchbase.repository;

import com.tybootcamp.couchbase.domain.Seller;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends CouchbaseRepository<Seller, String> {

  void deleteByName(String name);
  Seller findByName(String name);

}
