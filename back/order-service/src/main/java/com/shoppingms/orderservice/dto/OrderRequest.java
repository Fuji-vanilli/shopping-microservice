package com.shoppingms.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class OrderRequest {
    private String codeOrder;
    private List<String> lineItemCode;
}
