package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.OrderLineItem;
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
public class OrderResponse {
    private String id;
    private String codeOrder;
    private String lineItemCode;
    private Instant date;
    private BigDecimal totalPrice;
    private List<OrderLineItem> orderLineItems;
    private List<String> codeOrderLineItems;
}
