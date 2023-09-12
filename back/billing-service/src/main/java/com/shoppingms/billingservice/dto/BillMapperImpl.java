package com.shoppingms.billingservice.dto;

import com.shoppingms.billingservice.model.Bill;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BillMapperImpl implements BillMapper{
    @Override
    public Bill mapToBill(BillRequest request) {
        return Bill.builder()
                .code(request.getCode())
                .codeOrder(request.getCodeOrder())
                .emailClient(request.getEmailClient())
                .methodPayment(request.getMethodPayment())
                .build();
    }

    @Override
    public BillResponse mapToBillResponse(Bill bill) {
        return BillResponse.builder()
                .code(bill.getCode())
                .codeOrder(bill.getCodeOrder())
                .emailClient(bill.getEmailClient())
                .client(bill.getClient())
                .order(bill.getOrder())
                .methodPayment(bill.getMethodPayment())
                .build();
    }
}
