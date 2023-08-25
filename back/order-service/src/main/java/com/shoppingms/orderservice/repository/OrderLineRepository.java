package com.shoppingms.orderservice.repository;

import com.shoppingms.orderservice.model.OrderLine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderLineRepository extends MongoRepository<OrderLine, String> {
    Optional<OrderLine> findByCode(String code);
    boolean existsByCode(String code);
    void deleteByCode(String code);
    List<OrderLine> findByCodeIn(List<String> code);

}
