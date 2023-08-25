package com.shoppingms.orderservice.controller;

import com.shoppingms.orderservice.service.OrderLineService;
import com.shoppingms.orderservice.utils.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
@RequiredArgsConstructor
public class TestController {
    private final OrderLineService service;

    @GetMapping("get/{code}")
    public Response get(@PathVariable String code){
        return service.get(code);
    }

}
