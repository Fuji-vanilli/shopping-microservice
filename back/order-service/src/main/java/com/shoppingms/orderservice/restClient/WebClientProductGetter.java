package com.shoppingms.orderservice.restClient;

import com.shoppingms.orderservice.dto.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientProductGetter {
    private final WebClient.Builder webClient;

    public Product getProduct(String code) throws JSONException {
        CompletableFuture<String> dataFuture= webClient.build().get()
                .uri("http://localhost:7100/api/product/get/"+code)
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();

        String dataBrute= "";
        try {
            dataBrute= dataFuture.get();
        } catch (InterruptedException  | ExecutionException e) {
            log.error("error to fetch product microservice");
            throw new RuntimeException(e);
        }

        JSONObject dataJson= new JSONObject(dataBrute);
        JSONObject data= dataJson.getJSONObject("data").getJSONObject("product");

        return Product.builder()
                .name(data.getString("name"))
                .code(data.getString("code"))
                .price(BigDecimal.valueOf(data.getDouble("price")))
                .build();
    }
}
