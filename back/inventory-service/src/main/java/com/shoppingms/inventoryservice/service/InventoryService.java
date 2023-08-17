package com.shoppingms.inventoryservice.service;

import com.shoppingms.inventoryservice.dto.InventoryRequest;
import com.shoppingms.inventoryservice.utils.Response;

public interface InventoryService {
    Response add(InventoryRequest request);
    Response get(String code);
    Response subtract(InventoryRequest request);
    boolean isInStock(String code);
    Response all();
    Response delete(String code);
}
