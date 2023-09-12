package com.shoppingms.billingservice.service;

import com.shoppingms.billingservice.dto.BillMapper;
import com.shoppingms.billingservice.dto.BillRequest;
import com.shoppingms.billingservice.repository.BillRepository;
import com.shoppingms.billingservice.utils.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService{
    private final BillRepository billRepository;
    private final BillMapper billMapper;
    @Override
    public Response add(BillRequest request) {
        return null;
    }

    @Override
    public Response get(String code) {
        return null;
    }

    @Override
    public Response all() {
        return null;
    }

    @Override
    public Response delete(String code) {
        return null;
    }
}
