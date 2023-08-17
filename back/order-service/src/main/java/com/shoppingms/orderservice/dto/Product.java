package com.shoppingms.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Product {
    private String code;
    private String name;
    private BigDecimal price;
}
