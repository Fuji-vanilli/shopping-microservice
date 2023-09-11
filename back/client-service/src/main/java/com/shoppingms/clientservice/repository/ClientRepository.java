package com.shoppingms.clientservice.repository;

import com.shoppingms.clientservice.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ClientRepository extends MongoRepository<Client, String> {
    Client findByCode(String code);
    boolean existsByEmail(String email);
    void deleteByCode(String email);
}
