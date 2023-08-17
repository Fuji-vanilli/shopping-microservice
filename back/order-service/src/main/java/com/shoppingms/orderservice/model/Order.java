package com.shoppingms.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@Document(value = "order")
public class Order {
    @Id
    private String id;
    private String codeOrder;
    private String lineItemCode;
    private Instant date;
    private BigDecimal totalPrice;
    private List<String> codeOrderLineItems;
    private List<OrderLineItem> orderLineItems;

}
