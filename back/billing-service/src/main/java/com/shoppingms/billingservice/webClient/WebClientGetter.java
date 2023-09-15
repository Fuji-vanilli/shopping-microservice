package com.shoppingms.billingservice.webClient;

import com.shoppingms.billingservice.dto.Adress;
import com.shoppingms.billingservice.dto.Client;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Configuration
@RequiredArgsConstructor
public class WebClientGetter {
    private final WebClient.Builder webClient;

    public Client getClient(String email){
        final CompletableFuture<String> dataFuture = webClient.build().get().uri("http://localhost:7070/api/client/get/" +email)
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();

        String brute= "";
        JSONObject data= null;
        JSONObject addressJson= null;
        Client client= null;

        try {
            brute= dataFuture.get();
            JSONObject jsonObject= new JSONObject(brute);
            data= jsonObject.getJSONObject("data").getJSONObject("client");
            addressJson= data.getJSONObject("adress");
        } catch (InterruptedException | ExecutionException | JSONException e) {
            throw new RuntimeException(e);
        }

        try {
            Adress adress= Adress.builder()
                    .adress1(addressJson.getString("adress1"))
                    .adress2(addressJson.getString("adress2"))
                    .city(addressJson.getString("city"))
                    .country(addressJson.getString("country"))
                    .build();

            client= Client.builder()
                    .code(data.getString("code"))
                    .photo(data.getString("photo"))
                    .firstname(data.getString("firstname"))
                    .lastname(data.getString("lastname"))
                    .phoneNumber(data.getString("phoneNumber"))
                    .adress(adress)
                    .build();

        } catch (JSONException e) {
            throw new RuntimeException("error to deserialize address object");
        }
        return client;
    }

}
