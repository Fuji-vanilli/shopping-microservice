package com.shoppingms.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private List<OrderLineItem> orderLineItems;

}
