package com.shoppingms.productservice.service;

import com.shoppingms.productservice.dto.ProductMapper;
import com.shoppingms.productservice.dto.ProductRequest;
import com.shoppingms.productservice.model.Product;
import com.shoppingms.productservice.repository.ProductRepository;
import com.shoppingms.productservice.utils.Response;
import com.shoppingms.productservice.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public Response add(ProductRequest request) {
        List<String> errors= ProductValidator.validate(request);
        if(!errors.isEmpty()){
            log.error("some field not valid!!! Please try again");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "some field not valid...Please try again!"
            );
        }
        if(productRepository.existsByCode(request.getCode())){
            log.error("product already exist on the database!!!!");
            return generateResponse(
                    HttpStatus.CONFLICT,
                    null,
                    null,
                    "Product already exist...Please try again!"
            );
        }
        Product product= productMapper.mapToProduct(request);

        product.setCreationDate(Instant.now());
        product.setLastModifiedDate(Instant.now());

        productRepository.save(product);

        URI location= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{code}")
                .buildAndExpand("/api/product/"+request.getCode())
                .toUri();

        return generateResponse(
                HttpStatus.OK,
                location,
                Map.of(
                        "product", productMapper.mapToProductResponse(product)
                ),
                "new product added successfully!"
        );
    }

    @Override
    public Response get(String code) {
        Optional<Product> product= productRepository.findByCode(code);
        if(product.isEmpty()){
            log.error("product doesn't exist on the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "product doesn't exist on the database"
            );
        }

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "product", productMapper.mapToProductResponse(product.get())
                ),
                "product with the code: "+code+" getting successfully!"
        );
    }

    @Override
    public Response all() {
        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "products", productRepository.findAll().stream()
                                .map(productMapper::mapToProductResponse)
                                .toList()
                ),
                "all product getting successfully!"
        );
    }

    @Override
    public Response delete(String code) {
        Optional<Product> product= productRepository.findByCode(code);
        if(product.isEmpty()){
            log.error("product doesn't exist on the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "product doesn't exist on the database"
            );
        }
        productRepository.deleteByCode(code);
        return generateResponse(
                HttpStatus.OK,
                null,
                null,
                "product with the code: "+code+" deleted successfully!"
        );
    }
    private Response generateResponse(HttpStatus status, URI location, Map<?, ?> data, String message) {
        return Response.builder()
                .timeStamp(LocalDateTime.now())
                .status(status)
                .statusCode(status.value())
                .data(data)
                .location(location)
                .message(message)
                .build();
    }
}