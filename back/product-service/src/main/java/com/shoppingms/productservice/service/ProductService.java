package com.shoppingms.productservice.service;

import com.shoppingms.productservice.dto.ProductRequest;
import com.shoppingms.productservice.utils.Response;

public interface ProductService {
    Response add(ProductRequest request);
    Response get(String code);
    Response all();
    Response delete(String code);
}
