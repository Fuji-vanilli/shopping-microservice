package com.shoppingms.orderservice.dto;

import com.shoppingms.orderservice.model.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class OrderRequest {
    private String code;
    private List<String> codeOrderLines;
}
