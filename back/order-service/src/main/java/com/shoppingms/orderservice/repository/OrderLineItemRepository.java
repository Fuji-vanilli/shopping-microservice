package com.shoppingms.orderservice.repository;

import com.shoppingms.orderservice.model.OrderLineItem;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderLineItemRepository extends MongoRepository<OrderLineItem, String> {
    Optional<OrderLineItem> findByCode(String code);
    boolean existsByCode(String code);
    void deleteByCode(String code);
    List<OrderLineItem> findByCodeIn(List<String> codes);
}
