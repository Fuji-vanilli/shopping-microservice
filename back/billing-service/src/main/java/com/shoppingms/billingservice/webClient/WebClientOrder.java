package com.shoppingms.billingservice.webClient;

import com.shoppingms.billingservice.dto.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientOrder {
    private final WebClient.Builder webClient;

    public Order getOrder(String code){
        return webClient.build().get().uri("http://localhost:7140/api/order/getOrder/" + code)
                .retrieve()
                .bodyToMono(Order.class)
                .block();
    }
}
