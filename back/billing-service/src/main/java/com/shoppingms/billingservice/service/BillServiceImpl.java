package com.shoppingms.billingservice.service;

import com.shoppingms.billingservice.dto.BillMapper;
import com.shoppingms.billingservice.dto.BillRequest;
import com.shoppingms.billingservice.model.Bill;
import com.shoppingms.billingservice.repository.BillRepository;
import com.shoppingms.billingservice.utils.Response;
import com.shoppingms.billingservice.validator.BillValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService{
    private final BillRepository billRepository;
    private final BillMapper billMapper;
    private final GenerateReporte generateReporte;
    @Override
    public Response add(BillRequest request) throws FileNotFoundException {
        List<String> errors= BillValidator.validate(request);
        if(!errors.isEmpty()){
            log.error("some field errors...Please try again");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    Map.of(
                            "errors", errors
                    ),
                    "some field errors...Please try again"
            );
        }

        Bill bill= billMapper.mapToBill(request);
        generateReporte.generate(bill);

        URI location= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("{code}")
                .buildAndExpand("api/bill/"+bill.getCode())
                .toUri();

        return generateResponse(
                HttpStatus.OK,
                location,
                Map.of(
                        "bill", billMapper.mapToBillResponse(bill)
                ),
                "bill generated successfully"
        );
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
    private Response generateResponse(HttpStatus status, URI location, Map<?, ?> data, String message) {
        return Response.builder()
                .timeStamp(LocalDateTime.now())
                .status(status)
                .statusCode(status.value())
                .data(data)
                .location(location)
                .message(message)
                .build();
    }
}
