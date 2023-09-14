package com.shoppingms.billingservice.service;

import com.shoppingms.billingservice.dto.BillRequest;
import com.shoppingms.billingservice.utils.Response;

import java.io.FileNotFoundException;

public interface BillService {
    Response add(BillRequest request) throws FileNotFoundException;
    Response get(String code);
    Response all();
    Response delete(String code);
}
