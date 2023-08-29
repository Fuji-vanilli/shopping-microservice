package com.shoppingms.gatewayservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/inventoryServiceFallback")
    public String inventoryServiceFallback(){
        return "service inventory is fall down!!!!";
    }
    @PostMapping("/inventoryServiceFallback")
    public String productServiceFallback(){
        return "service inventory is fall down!!!!";
    }
    @GetMapping("/inventoryFallback")
    public String inventoryFallback(){
        return "Error to get the service inventory!!!";
    }
}
