package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.Order;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMapperImpl implements OrderMapper{
    @Override
    public Order mapToOrder(OrderRequest request) {
        return Order.builder()
                .codeOrder(request.getCodeOrder())
                .codeOrderLineItems(request.getLineItemCode())
                .build();
    }

    @Override
    public OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .codeOrder(order.getCodeOrder())
                .lineItemCode(order.getLineItemCode())
                .date(order.getDate())
                .totalPrice(order.getTotalPrice())
                .codeOrderLineItems(order.getCodeOrderLineItems())
                .orderLineItems(order.getOrderLineItems())
                .build();
    }
}
