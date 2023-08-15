package com.shoppingms.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@Document(value = "product")
public class Product {
    @Id
    private String id;
    private String code;
    private Instant creationDate;
    private Instant lastModifiedDate;
    private String name;
    private String description;
    private BigDecimal price;
}
