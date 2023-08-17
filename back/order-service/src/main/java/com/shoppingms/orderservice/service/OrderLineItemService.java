package com.shoppingms.orderservice.service;

import com.shoppingms.orderservice.dto.OrderLineItemRequest;
import com.shoppingms.orderservice.dto.OrderRequest;
import com.shoppingms.orderservice.utils.Response;
import org.json.JSONException;

public interface OrderLineItemService {
    Response add(OrderLineItemRequest request) throws JSONException;
    Response get(String code);
    Response all();
    Response delete(String code);
}
