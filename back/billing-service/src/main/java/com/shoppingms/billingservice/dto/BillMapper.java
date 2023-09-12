package com.shoppingms.billingservice.dto;

import com.shoppingms.billingservice.model.Bill;

public interface BillMapper {
    Bill mapToBill(BillRequest request);
    BillResponse mapToBillResponse(Bill bill);
}
