package com.shoppingms.clientservice.dto;

import com.shoppingms.clientservice.model.Client;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientMapperImpl implements ClientMapper{
    @Override
    public Client mapToClient(ClientRequest request) {
        return Client.builder()
                .code(request.getCode())
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phoneNumber(request.getPhoneNumber())
                .adress(request.getAdress())
                .photo(request.getPhoto())
                .build();
    }

    @Override
    public ClientResponse mapToClientResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .firstname(client.getFirstname())
                .lastname(client.getLastname())
                .code(client.getCode())
                .adress(client.getAdress())
                .createdDate(client.getCreatedDate())
                .email(client.getEmail())
                .photo(client.getPhoto())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }
}
