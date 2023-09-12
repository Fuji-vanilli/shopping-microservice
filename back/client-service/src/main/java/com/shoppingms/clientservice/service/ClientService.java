package com.shoppingms.clientservice.service;

import com.shoppingms.clientservice.dto.ClientRequest;
import com.shoppingms.clientservice.utils.Response;

public interface ClientService {
    Response add(ClientRequest request);
    Response get(String email);
    Response all();
    Response delete(String code);
}
