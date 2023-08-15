package com.shoppingms.productservice.repository;

import com.shoppingms.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByCode(String code);
    boolean existsByCode(String code);
    void deleteByCode(String code);
}
