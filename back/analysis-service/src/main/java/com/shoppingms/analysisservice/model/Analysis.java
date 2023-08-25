package com.shoppingms.analysisservice.model;

import com.shoppingms.analysisservice.dto.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@Document(value = "analysis")
public class Analysis {
    private String id;
    private String codeProduct;
    private Product product;
    private BigDecimal percentage;
}
