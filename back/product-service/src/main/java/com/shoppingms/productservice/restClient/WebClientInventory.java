package com.shoppingms.productservice.restClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientInventory {
    private final WebClient.Builder webClient;

    public Boolean productToInventory(String code) throws JSONException {
        JSONObject paramsJson= new JSONObject();
        paramsJson.put("codeProduct", code);
        paramsJson.put("quantity", 0);

        CompletableFuture<String> dataFuture = webClient.build().post()
                .uri("http://INVENTORY-SERVICE/api/inventory/add")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(paramsJson.toString()))
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();

        String data= "";

        try {
            data= dataFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("error to deserialize inventory service");
            throw new RuntimeException(e);
        }
        JSONObject dataJson= new JSONObject(data);

        return dataJson.getInt("statusCode")== 200;
    }
}
