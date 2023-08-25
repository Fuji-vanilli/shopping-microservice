package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class OrderResponse {
    private String id;
    private String code;
    private BigDecimal totalPrice;
    private List<String> codeOrderLines;
    private List<OrderLine> orderLines;
}
