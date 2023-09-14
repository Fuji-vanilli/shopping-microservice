package com.shoppingms.orderservice.service;

import com.shoppingms.orderservice.dto.OrderRequest;
import com.shoppingms.orderservice.dto.OrderResponse;
import com.shoppingms.orderservice.utils.Response;

public interface OrderService {
    Response add(OrderRequest request);
    Response get(String code);
    OrderResponse getOrder(String code);
    Response all();
    Response delete(String code);
}
