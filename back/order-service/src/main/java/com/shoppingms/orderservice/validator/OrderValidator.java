package com.shoppingms.orderservice.validator;

import com.shoppingms.orderservice.dto.OrderLineRequest;
import com.shoppingms.orderservice.dto.OrderRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderValidator {
    public static List<String> validate(OrderRequest request){
        List<String> errors= new ArrayList<>();

        if(Objects.isNull(request.getCode())){
            errors.add("code required!");
        }
        if(Objects.isNull(request.getCodeOrderLines())){
            errors.add("list order lines required!");
        }
        return errors;
    }
}
