package com.shoppingms.orderservice.repository;

import com.shoppingms.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByCode(String code);
    boolean existsByCode(String code);
    void deleteByCode(String code);
}
