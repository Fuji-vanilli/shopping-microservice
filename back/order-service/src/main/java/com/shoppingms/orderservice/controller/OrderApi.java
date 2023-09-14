package com.shoppingms.orderservice.controller;

import com.shoppingms.orderservice.dto.OrderRequest;
import com.shoppingms.orderservice.dto.OrderResponse;
import com.shoppingms.orderservice.service.OrderService;
import com.shoppingms.orderservice.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shoppingms.orderservice.utils.Root.APP_ROOT_ORDER;

@RestController
@RequestMapping(APP_ROOT_ORDER)
@RequiredArgsConstructor
public class OrderApi implements OrderController{
    private final OrderService orderService;
    @Override
    public ResponseEntity<Response> add(OrderRequest request) {
        return ResponseEntity.ok(orderService.add(request));
    }

    @Override
    public ResponseEntity<Response> get(String code) {
        return ResponseEntity.ok(orderService.get(code));
    }

    @Override
    public OrderResponse getOrder(String code) {
        return orderService.getOrder(code);
    }

    @Override
    public ResponseEntity<Response> all() {
        return ResponseEntity.ok(orderService.all());
    }

    @Override
    public ResponseEntity<Response> delete0(String code) {
        return ResponseEntity.ok(orderService.delete(code));
    }
}
