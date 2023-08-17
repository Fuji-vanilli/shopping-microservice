package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<OrderLineItem> orderLineItems;
}
