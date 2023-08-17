package com.shoppingms.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class OrderLineItemResponse {
    private String id;
    private String code;
    private String codeProduct;
    private BigDecimal quantity;
    private BigDecimal price;
    private Product product;
}
