package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.Order;

public interface OrderMapper {
    Order mapToOrder(OrderRequest request);
    OrderResponse mapToOrderResponse(Order order);
}
