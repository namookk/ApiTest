package com.github.prgrms.apitest.products;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(long id);

    List<Product> findAll();

}