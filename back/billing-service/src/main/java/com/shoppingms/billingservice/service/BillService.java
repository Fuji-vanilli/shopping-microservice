package com.shoppingms.billingservice.service;

import com.shoppingms.billingservice.dto.BillRequest;
import com.shoppingms.billingservice.utils.Response;

public interface BillService {
    Response add(BillRequest request);
    Response get(String code);
    Response all();
    Response delete(String code);
}
