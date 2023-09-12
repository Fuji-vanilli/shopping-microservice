package com.shoppingms.billingservice.repository;

import com.shoppingms.billingservice.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByCode(String code);
    void deleteByCode(String code);
}
