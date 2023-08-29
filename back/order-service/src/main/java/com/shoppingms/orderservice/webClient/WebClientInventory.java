package com.shoppingms.orderservice.webClient;

import com.shoppingms.orderservice.dto.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientInventory {
    private final WebClient.Builder webClient;

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "isInStockFallback")
    public Boolean isInStock(String code){
        return webClient.build().get()
                .uri("http://INVENTORY-SERVICE/api/inventory/inStock/"+code)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "getQuantityFallback")
    public BigDecimal getQuantity(String code){
        final CompletableFuture<String> future = webClient.build().get()
                .uri("http://INVENTORY-SERVICE/api/inventory/get/" + code)
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
        BigDecimal quantity= null;

        try {
            JSONObject dataJson= new JSONObject(brute);
            data= dataJson.getJSONObject("data").getJSONObject("inventory");

            quantity= BigDecimal.valueOf(data.getDouble("quantity"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return quantity;
    }

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "subtractFallback")
    public Boolean subtract(String codeProduct, BigDecimal quantity){
        JSONObject params= null;

        try {
            params= new JSONObject();
            params.put("codeProduct", codeProduct);
            params.put("quantity", quantity);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        final CompletableFuture<String> future = webClient.build().post()
                .uri("http://INVENTORY-SERVICE/api/inventory/subtract")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(params.toString()))
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();

        String brute= "";
        try {
            brute= future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("error to fetch inventory service subtract....");
            throw new RuntimeException(e);
        }

        int statusCode= 0;

        try {
            JSONObject data= new JSONObject(brute);
            statusCode= data.getInt("statusCode");
        } catch (JSONException e) {
            log.error("error to deserialize the JSON object");
            throw new RuntimeException(e);
        }

        return statusCode== 200;
    }
    public Boolean isInStockFallback(String code, Exception e){
        return false;
    }
    public BigDecimal getQuantityFallback(String code, Exception e){
        return BigDecimal.ZERO;
    }
    public Boolean subtractFallback(String code, BigDecimal quantity, Exception e){
        return false;
    }
}
