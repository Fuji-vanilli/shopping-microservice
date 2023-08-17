package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.OrderLineItem;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderLineItemMapperImpl implements OrderLineItemMapper {
    @Override
    public OrderLineItem mapToOrderLineItem(OrderLineItemRequest request) {
        return OrderLineItem.builder()
                .code(request.getCode())
                .codeProduct(request.getCodeProduct())
                .quantity(request.getQuantity())
                .build();
    }

    @Override
    public OrderLineItemResponse mapToOrderLineItemResponse(OrderLineItem orderLineItem) {
        return OrderLineItemResponse.builder()
                .id(orderLineItem.getId())
                .code(orderLineItem.getCode())
                .codeProduct(orderLineItem.getCodeProduct())
                .product(orderLineItem.getProduct())
                .price(orderLineItem.getPrice())
                .quantity(orderLineItem.getQuantity())
                .build();
    }
}
