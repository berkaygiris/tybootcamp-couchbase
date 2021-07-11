package com.tybootcamp.couchbase.repository;

import com.couchbase.client.java.query.QueryScanConsistency;
import com.tybootcamp.couchbase.domain.Product;
import com.tybootcamp.couchbase.domain.Seller;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.ScanConsistency;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends CouchbaseRepository<Seller, String> {

  @ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
  void deleteByName(String name);
  @ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
  Optional<Seller> findByName(String name);

  @ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
  Seller save(Seller entity);

}
