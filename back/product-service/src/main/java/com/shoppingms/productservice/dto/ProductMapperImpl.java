package com.shoppingms.productservice.dto;

import com.shoppingms.productservice.model.Product;
import com.shoppingms.productservice.repository.ProductRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductMapperImpl implements ProductMapper{
    @Override
    public Product mapToProduct(ProductRequest request) {
        return Product.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    @Override
    public ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .creationDate(product.getCreationDate())
                .lastModifiedDate(product.getLastModifiedDate())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
