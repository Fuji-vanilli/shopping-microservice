package com.shoppingms.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class OrderRequest {
    private String codeOrder;
    private String lineItemCode;
}
