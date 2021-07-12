//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tybootcamp.couchbase.repository;

import com.tybootcamp.couchbase.domain.Seller;
import java.util.Optional;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends CouchbaseRepository<Seller, String> {
  void deleteByName(String name);

  Optional<Seller> findByName(String name);
}
