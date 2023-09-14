package com.shoppingms.orderservice.controller;

import com.shoppingms.orderservice.dto.OrderRequest;
import com.shoppingms.orderservice.dto.OrderResponse;
import com.shoppingms.orderservice.utils.Response;
import jakarta.ws.rs.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface OrderController {
    @PostMapping("add")
    ResponseEntity<Response> add(@RequestBody OrderRequest request);
    @GetMapping("get/{code}")
    ResponseEntity<Response> get(@PathVariable String code);
    @GetMapping("getOrder/{code}")
    OrderResponse getOrder(@PathVariable String code);
    @GetMapping("all")
    ResponseEntity<Response> all();
    @DeleteMapping("delete/{code}")
    ResponseEntity<Response> delete0(@PathVariable String code);
}
