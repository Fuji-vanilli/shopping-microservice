package com.shoppingms.clientservice.repository;

import com.shoppingms.clientservice.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface ClientRepository extends MongoRepository<Client, String> {
    Optional<Client> findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteByCode(String email);
}
