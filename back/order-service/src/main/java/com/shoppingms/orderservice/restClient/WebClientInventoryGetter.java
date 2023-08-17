package com.shoppingms.orderservice.restClient;

import jakarta.ws.rs.core.MultivaluedMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientInventoryGetter {
    private final WebClient.Builder webClient;

    public Boolean isInStock(String code){
        return webClient.build().get()
                .uri("http://localhost:7120/api/inventory/inStock/"+code)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
    public BigDecimal quantity(String code) throws JSONException {
        CompletableFuture<String> dataFuture= webClient.build().get()
                .uri("http://localhost:7120/api/inventory/get/"+code)
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();

        String dataBrute= "";
        try {
            dataBrute= dataFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("error to fetch product microservice");
            throw new RuntimeException(e);
        }

        JSONObject dataJson= new JSONObject(dataBrute);
        JSONObject data= dataJson.getJSONObject("data").getJSONObject("inventory");

        return BigDecimal.valueOf(data.getDouble("quantity"));
    }
    public Boolean subtractQuantity(String code, BigDecimal quantity) throws JSONException {
        JSONObject requestJson= new JSONObject();
        requestJson.put("codeProduct", code);
        requestJson.put("quantity", quantity);

        CompletableFuture<String> dataFuture = webClient.build().post()
                .uri("http://localhost:7120/api/inventory/subtract")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestJson.toString()))
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();

        String dataBrute= "";

        try {
            dataBrute= dataFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("error to fetch the inventory microservice!");
            throw new RuntimeException(e);
        }
        JSONObject dataJson= new JSONObject(dataBrute);
        return dataJson.getInt("statusCode") == 200;
    }
}
