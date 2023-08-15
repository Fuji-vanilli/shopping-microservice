package com.shoppingms.productservice.dto;

import com.shoppingms.productservice.model.Product;

public interface ProductMapper {
    Product mapToProduct(ProductRequest request);
    ProductResponse mapToProductResponse(Product product);
}
