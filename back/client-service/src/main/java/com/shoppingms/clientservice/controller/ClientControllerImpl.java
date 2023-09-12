package com.shoppingms.clientservice.controller;

import com.shoppingms.clientservice.dto.ClientRequest;
import com.shoppingms.clientservice.service.ClientService;
import com.shoppingms.clientservice.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shoppingms.clientservice.utils.Root.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT)
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController{
    private final ClientService clientService;

    @Override
    public ResponseEntity<Response> add(ClientRequest request) {
        return ResponseEntity.ok(clientService.add(request));
    }

    @Override
    public ResponseEntity<Response> get(String code) {
        return ResponseEntity.ok(clientService.get(code));
    }

    @Override
    public ResponseEntity<Response> all() {
        return ResponseEntity.ok(clientService.all());
    }

    @Override
    public ResponseEntity<Response> delete(String code) {
        return ResponseEntity.ok(clientService.delete(code));
    }
}
