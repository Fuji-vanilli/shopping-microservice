package com.shoppingms.orderservice.controller;

import com.shoppingms.orderservice.dto.OrderLineRequest;
import com.shoppingms.orderservice.service.OrderLineService;
import com.shoppingms.orderservice.utils.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shoppingms.orderservice.utils.Root.APP_ROOT_ORDER_LINE;

@RestController
@RequestMapping(APP_ROOT_ORDER_LINE)
@RefreshScope
@RequiredArgsConstructor
public class OrderLineApi implements OrderLineController{
    private final OrderLineService orderLineService;

    @Override
    public ResponseEntity<Response> add(OrderLineRequest request) {
        return ResponseEntity.ok(orderLineService.add(request));
    }

    @Override

    public ResponseEntity<Response> get(String code) {
        return ResponseEntity.ok(orderLineService.get(code));
    }

    @Override
    public ResponseEntity<Response> all() {
        return ResponseEntity.ok(orderLineService.all());
    }

    @Override
    public ResponseEntity<Response> delete(String code) {
        return ResponseEntity.ok(orderLineService.delete(code));
    }

}
