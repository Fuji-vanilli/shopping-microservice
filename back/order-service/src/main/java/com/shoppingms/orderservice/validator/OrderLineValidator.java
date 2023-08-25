package com.shoppingms.orderservice.validator;

import com.shoppingms.orderservice.dto.OrderLineRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderLineValidator {
    public static List<String> validate(OrderLineRequest request){
        List<String> errors= new ArrayList<>();

        if(Objects.isNull(request.getCodeProduct())){
            errors.add("code required!");
        }
        if(Objects.isNull(request.getQuantity())){
            errors.add("quantity required!");
        }
        return errors;
    }
}
