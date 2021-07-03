package com.tybootcamp.couchbase.repository;

import com.tybootcamp.couchbase.domain.Seller;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends CouchbaseRepository<Seller, String> {

  void deleteByName(String name);
}
