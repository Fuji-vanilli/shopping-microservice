package com.shoppingms.orderservice.repository;

import com.shoppingms.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByCodeOrder(String code);
    boolean existsByCodeOrder(String code);
    void deleteByCodeOrder(String code);
}
