package com.shoppingms.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Order {
    private String code;
    private Instant date;
    private BigDecimal totalPrice;
    private List<String> codeOrderLines;
}
