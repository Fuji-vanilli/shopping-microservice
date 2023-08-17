package com.shoppingms.inventoryservice.dto;

import com.shoppingms.inventoryservice.model.Inventory;

public interface InventoryMapper {
    Inventory mapToInventory(InventoryRequest request);
    InventoryResponse mapToInventoryResponse(Inventory inventory);
}
