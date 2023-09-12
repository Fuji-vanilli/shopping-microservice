package com.shoppingms.billingservice.controller;

import com.shoppingms.billingservice.dto.BillRequest;
import com.shoppingms.billingservice.service.BillService;
import com.shoppingms.billingservice.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shoppingms.billingservice.utils.Root.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT)
@RequiredArgsConstructor
public class BillApi implements BillController{
    private final BillService billService;

    @Override
    public ResponseEntity<Response> add(BillRequest request) {
        return ResponseEntity.ok(billService.add(request));
    }

    @Override
    public ResponseEntity<Response> get(String code) {
        return ResponseEntity.ok(billService.get(code));
    }

    @Override
    public ResponseEntity<Response> all() {
        return ResponseEntity.ok(billService.all());
    }

    @Override
    public ResponseEntity<Response> delete(String code) {
        return ResponseEntity.ok(billService.delete(code));
    }
}
