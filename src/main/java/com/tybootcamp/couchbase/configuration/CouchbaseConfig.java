package com.tybootcamp.couchbase.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

  @Override
  public String getConnectionString() {
    return "localhost";
  }

  @Override
  public String getUserName() {
    return "myapp";
  }

  @Override
  public String getPassword() {
    return "123321";
  }

  @Override
  public String getBucketName() {
    return "sellers";
  }

}
