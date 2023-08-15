package com.shoppingms.productservice.controller;

import com.shoppingms.productservice.dto.ProductRequest;
import com.shoppingms.productservice.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ProductController {
    @PostMapping("add")
    ResponseEntity<Response> add(@RequestBody ProductRequest request);
    @GetMapping("get/{code}")
    ResponseEntity<Response> get(@PathVariable String code);
    @GetMapping("all")
    ResponseEntity<Response> all();
    @DeleteMapping("delete/{code}")
    ResponseEntity<Response> delete(@PathVariable String code);
}
