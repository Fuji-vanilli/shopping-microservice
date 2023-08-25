package com.shoppingms.orderservice.model;

import com.shoppingms.orderservice.dto.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@Document(value = "order_line")
public class OrderLine {
    @Id
    private String id;
    private String code;
    private String codeProduct;
    private BigDecimal quantity;
    private BigDecimal price;
    private Product product;
}
