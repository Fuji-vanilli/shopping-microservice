package com.shoppingms.clientservice.dto;

import com.shoppingms.clientservice.model.Client;

public interface ClientMapper {
    Client mapToClient(ClientRequest request);
    ClientResponse mapToClientResponse(Client client);
}
