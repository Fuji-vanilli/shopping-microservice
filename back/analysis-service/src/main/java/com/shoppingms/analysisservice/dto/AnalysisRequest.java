package com.shoppingms.analysisservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AnalysisRequest {
    private String codeProduct;
    private BigDecimal percentage;
}
