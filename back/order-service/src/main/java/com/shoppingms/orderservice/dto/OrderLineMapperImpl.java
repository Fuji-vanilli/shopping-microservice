package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.OrderLine;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderLineMapperImpl implements OrderLineMapper{
    @Override
    public OrderLine mapToOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .code(request.getCode())
                .codeProduct(request.getCodeProduct())
                .quantity(request.getQuantity())
                .build();
    }

    @Override
    public OrderLineResponse mapToOrderLineResponse(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .id(orderLine.getId())
                .code(orderLine.getCode())
                .codeProduct(orderLine.getCodeProduct())
                .price(orderLine.getPrice())
                .quantity(orderLine.getQuantity())
                .product(orderLine.getProduct())
                .build();
    }
}
