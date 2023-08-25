package com.shoppingms.productservice.controller;

import com.shoppingms.productservice.dto.ProductRequest;
import com.shoppingms.productservice.model.Product;
import com.shoppingms.productservice.service.ProductService;
import com.shoppingms.productservice.utils.Response;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shoppingms.productservice.utils.Root.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT)
@RequiredArgsConstructor
public class ProductWeb implements ProductController {
    private final ProductService productService;
    @Override
    public ResponseEntity<Response> add(ProductRequest request) throws JSONException {
        return ResponseEntity.ok(productService.add(request));
    }

    @Override
    public ResponseEntity<Response> get(String code) {
        return ResponseEntity.ok(productService.get(code));
    }

    @Override
    public Product getProduct(String code) {
        return productService.getProduct(code);
    }

    @Override
    public ResponseEntity<Response> all() {
        return ResponseEntity.ok(productService.all());
    }

    @Override
    public ResponseEntity<Response> delete(String code) {
        return ResponseEntity.ok(productService.delete(code));
    }
}
