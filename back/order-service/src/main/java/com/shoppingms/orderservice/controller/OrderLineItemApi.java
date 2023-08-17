package com.shoppingms.orderservice.controller;

import com.shoppingms.orderservice.dto.OrderLineItemRequest;
import com.shoppingms.orderservice.dto.OrderRequest;
import com.shoppingms.orderservice.service.OrderLineItemService;
import com.shoppingms.orderservice.service.OrderService;
import com.shoppingms.orderservice.utils.Response;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shoppingms.orderservice.utils.Root.APP_ROOT_ORDER_LINE_ITEM;

@RestController
@RequestMapping(APP_ROOT_ORDER_LINE_ITEM)
@RequiredArgsConstructor
public class OrderLineItemApi implements OrderLineItemController{
    private final OrderLineItemService orderService;
    @Override
    public ResponseEntity<Response> add(OrderLineItemRequest request) throws JSONException {
        return ResponseEntity.ok(orderService.add(request));
    }

    @Override
    public ResponseEntity<Response> get(String code) {
        return ResponseEntity.ok(orderService.get(code));
    }

    @Override
    public ResponseEntity<Response> all() {
        return ResponseEntity.ok(orderService.all());
    }

    @Override
    public ResponseEntity<Response> delete(String code) {
        return ResponseEntity.ok(orderService.delete(code));
    }
}
