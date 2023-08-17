package com.shoppingms.inventoryservice.repository;

import com.shoppingms.inventoryservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Optional<Inventory> findByCodeProduct(String code);
    boolean existsByCodeProduct(String code);
    void deleteByCodeProduct(String code);
}
