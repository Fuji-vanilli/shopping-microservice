package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.OrderLine;

public interface OrderLineMapper {
    OrderLine mapToOrderLine(OrderLineRequest request);
    OrderLineResponse mapToOrderLineResponse(OrderLine orderLine);
}
