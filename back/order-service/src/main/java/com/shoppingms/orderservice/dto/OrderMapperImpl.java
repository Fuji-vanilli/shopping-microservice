package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.Order;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMapperImpl implements OrderMapper{
    @Override
    public Order mapToOrder(OrderRequest request) {
        return Order.builder()
                .code(request.getCode())
                .codeOrderLines(request.getCodeOrderLines())
                .build();
    }

    @Override
    public OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .code(order.getCode())
                .date(order.getDate())
                .codeOrderLines(order.getCodeOrderLines())
                .totalPrice(order.getTotalPrice())
                .orderLines(order.getOrderLines())
                .build();
    }
}
