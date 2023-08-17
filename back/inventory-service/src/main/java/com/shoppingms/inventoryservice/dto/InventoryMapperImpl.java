package com.shoppingms.inventoryservice.dto;

import com.shoppingms.inventoryservice.model.Inventory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryMapperImpl implements InventoryMapper{
    @Override
    public Inventory mapToInventory(InventoryRequest request) {
        return Inventory.builder()
                .codeProduct(request.getCodeProduct())
                .quantity(request.getQuantity())
                .build();
    }

    @Override
    public InventoryResponse mapToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .codeProduct(inventory.getCodeProduct())
                .quantity(inventory.getQuantity())
                .build();
    }
}
