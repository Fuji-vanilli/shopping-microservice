package com.shoppingms.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class InventoryResponse {
    private String id;
    private String codeProduct;
    private BigDecimal quantity;
}
