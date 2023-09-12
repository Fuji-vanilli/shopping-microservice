package com.shoppingms.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class BillResponse {
    private String code;
    private String methodPayment;
    private String codeOrder;
    private String emailClient;
    private Order order;
    private Client client;
}
