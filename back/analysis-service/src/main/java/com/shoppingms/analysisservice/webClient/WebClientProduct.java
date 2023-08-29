package com.shoppingms.analysisservice.webClient;

import com.shoppingms.analysisservice.dto.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientProduct {
    private final WebClient.Builder webClient;
    @CircuitBreaker(name = "productService", fallbackMethod = "productFallback")
    public Product getProduct(String code){
        final CompletableFuture<String> future = webClient.build().get()
                .uri("http://localhost:7100/api/product/get/" + code)
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();

        String brute= "";
        try {
            brute= future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("error to fetch the prodcut microservice");
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
                    .build();
        } catch (JSONException e) {
            log.error("error to convert on JSON object...!");
            throw new RuntimeException(e);
        }

        return product;

    }
    public Product productFallback(String code){
        return Product.builder()
                .name("null")
                .name("null")
                .build();
    }
}
