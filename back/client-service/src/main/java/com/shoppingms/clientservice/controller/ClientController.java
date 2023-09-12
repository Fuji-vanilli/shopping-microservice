package com.shoppingms.clientservice.controller;

import com.shoppingms.clientservice.dto.ClientRequest;
import com.shoppingms.clientservice.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ClientController {
    @PostMapping("add")
    ResponseEntity<Response> add(@RequestBody ClientRequest request);
    @GetMapping("get/{code}")
    ResponseEntity<Response> get(@PathVariable String email);
    @GetMapping("all")
    ResponseEntity<Response> add();
    @DeleteMapping("delete/{code}")
    ResponseEntity<Response> delete(@PathVariable String code);
}
