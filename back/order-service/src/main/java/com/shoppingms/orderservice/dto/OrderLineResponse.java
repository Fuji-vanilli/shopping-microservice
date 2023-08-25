package com.shoppingms.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class OrderLineResponse {
    private String id;
    private String codeProduct;
    private BigDecimal quantity;
    private BigDecimal price;
    private Product product;
    private String code;
}
