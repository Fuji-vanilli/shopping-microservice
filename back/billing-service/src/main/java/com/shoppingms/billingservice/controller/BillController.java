package com.shoppingms.billingservice.controller;

import com.shoppingms.billingservice.dto.BillRequest;
import com.shoppingms.billingservice.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

public interface BillController {
    @PostMapping("add")
    ResponseEntity<Response> add(@RequestBody BillRequest request) throws FileNotFoundException;
    @GetMapping("get/{code}")
    ResponseEntity<Response> get(@PathVariable String code);
    @GetMapping("all")
    ResponseEntity<Response> all();
    @DeleteMapping("delete/{code}")
    ResponseEntity<Response> delete(@PathVariable String code);
}
