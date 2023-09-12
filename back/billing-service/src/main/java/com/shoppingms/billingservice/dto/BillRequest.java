package com.shoppingms.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class BillRequest {
    private String code;
    private String methodPayment;
    private String codeOrder;
    private String emailClient;
}
