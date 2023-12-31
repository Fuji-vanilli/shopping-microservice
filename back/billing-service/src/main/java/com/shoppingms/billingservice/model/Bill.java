package com.shoppingms.billingservice.model;

import com.shoppingms.billingservice.dto.Client;
import com.shoppingms.billingservice.dto.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String methodPayment;
    private String codeOrder;
    private String emailClient;
    @Transient
    private Order order;
    @Transient
    private Client client;
}
