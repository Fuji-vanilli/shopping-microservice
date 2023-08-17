package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.OrderLineItem;

public interface OrderLineItemMapper {
    OrderLineItem mapToOrderLineItem(OrderLineItemRequest request);
    OrderLineItemResponse mapToOrderLineItemResponse(OrderLineItem orderLineItem);
}
