package com.shoppingms.inventoryservice.controller;

import com.shoppingms.inventoryservice.dto.InventoryRequest;
import com.shoppingms.inventoryservice.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface InventoryController {
    @PostMapping("add")
    ResponseEntity<Response> add(@RequestBody InventoryRequest request);
    @GetMapping("get/{code}")
    ResponseEntity<Response> get(@PathVariable String code);
    @PostMapping("subtract")
    ResponseEntity<Response> subtract(@RequestBody InventoryRequest request);
    @GetMapping("inStock/{code}")
    boolean isInStock(@PathVariable String code);
    @GetMapping("all")
    ResponseEntity<Response> all();
    @DeleteMapping("delete/{code}")
    ResponseEntity<Response> delete(@PathVariable String code);
}
