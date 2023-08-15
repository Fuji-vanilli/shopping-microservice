package com.shoppingms.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String code;
    private Instant creationDate;
    private Instant lastModifiedDate;
    private String name;
    private String description;
    private BigDecimal price;
}
