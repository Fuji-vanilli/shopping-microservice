package com.shoppingms.orderservice.controller;

import com.shoppingms.orderservice.dto.OrderLineItemRequest;
import com.shoppingms.orderservice.dto.OrderRequest;
import com.shoppingms.orderservice.utils.Response;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface OrderLineItemController {
    @PostMapping("add")
    ResponseEntity<Response> add(@RequestBody OrderLineItemRequest request) throws JSONException;
    @GetMapping("get/{code}")
    ResponseEntity<Response> get(@PathVariable String code);
    @GetMapping("all")
    ResponseEntity<Response> all();
    @DeleteMapping("delete/{code}")
    ResponseEntity<Response> delete(@PathVariable String code);
}
