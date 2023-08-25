package com.shoppingms.orderservice.webClient;

import com.shoppingms.orderservice.dto.Product;
import com.shoppingms.orderservice.utils.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientProduct {
    private final WebClient.Builder webClient;
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackAdd")
    public Product getProduct(String code){
        final CompletableFuture<String> future = webClient.build().get()
                .uri("http://PRODUCT-SERVICE/api/product/get/" + code)
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();

        String brute= "";
        try {
            brute= future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("error to fetch the product microservices...!");
            throw new RuntimeException(e);
        }

        JSONObject data= null;
        Product product= null;

        try {
            JSONObject dataJson= new JSONObject(brute);
            data= dataJson.getJSONObject("data").getJSONObject("product");

            product= Product.builder()
                    .code(data.getString("code"))
                    .name(data.getString("name"))
                    .price(BigDecimal.valueOf(data.getDouble("price")))
                    .build();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return product;
    }
    public Response addMethode(String code, Exception e){
        return Response.builder()
                .message("failed!!!!")
                .build();
    }
}
