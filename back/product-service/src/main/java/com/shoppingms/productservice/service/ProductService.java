package com.shoppingms.productservice.service;

import com.shoppingms.productservice.dto.ProductRequest;
import com.shoppingms.productservice.model.Product;
import com.shoppingms.productservice.utils.Response;
import org.json.JSONException;

public interface ProductService {
    Response add(ProductRequest request) throws JSONException;
    Response get(String code);
    Product getProduct(String code);
    Response all();
    Response delete(String code);
}
