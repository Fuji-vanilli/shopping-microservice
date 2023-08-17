package com.shoppingms.inventoryservice.controller;

import com.shoppingms.inventoryservice.dto.InventoryRequest;
import com.shoppingms.inventoryservice.repository.InventoryRepository;
import com.shoppingms.inventoryservice.service.InventoryService;
import com.shoppingms.inventoryservice.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shoppingms.inventoryservice.utils.Root.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT)
@RequiredArgsConstructor
public class InventoryApi implements InventoryController{
    private final InventoryService inventoryService;
    @Override
    public ResponseEntity<Response> add(InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.add(request));
    }

    @Override
    public ResponseEntity<Response> get(String code) {
        return ResponseEntity.ok(inventoryService.get(code));
    }

    @Override
    public ResponseEntity<Response> subtract(InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.subtract(request));
    }

    @Override
    public boolean isInStock(String code) {
        return inventoryService.isInStock(code);
    }

    @Override
    public ResponseEntity<Response> all() {
        return ResponseEntity.ok(inventoryService.all());
    }

    @Override
    public ResponseEntity<Response> delete(String code) {
        return ResponseEntity.ok(inventoryService.delete(code));
    }
}
