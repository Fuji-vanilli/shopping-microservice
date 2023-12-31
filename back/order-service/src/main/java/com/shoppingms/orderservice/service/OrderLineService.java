package com.shoppingms.orderservice.service;

import com.shoppingms.orderservice.dto.OrderLineRequest;
import com.shoppingms.orderservice.utils.Response;

public interface OrderLineService {
    Response add(OrderLineRequest request);
    Response get(String code);
    Response all();
    Response delete(String code);
}
