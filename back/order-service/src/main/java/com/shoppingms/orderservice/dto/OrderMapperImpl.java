package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.Order;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMapperImpl implements OrderMapper{
    @Override
    public Order mapToOrder(OrderRequest request) {
        return Order.builder()
                .codeOrder(request.getCodeOrder())
                .lineItemCode(request.getLineItemCode())
                .build();
    }

    @Override
    public OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .codeOrder(order.getCodeOrder())
                .lineItemCode(order.getLineItemCode())
                .date(order.getDate())
                .orderLineItems(order.getOrderLineItems())
                .build();
    }
}