package com.shoppingms.orderservice.validator;

import com.shoppingms.orderservice.dto.OrderLineItemRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderLineItemValidator {
    public static List<String> validate(OrderLineItemRequest request){
        List<String> errors= new ArrayList<>();

        if(Objects.isNull(request.getCodeProduct())){
            errors.add("code order line required!");
        }
        if(Objects.isNull(request.getQuantity())){
            errors.add("price required!");
        }
        if(Objects.isNull(request.getCode())){
            errors.add("quantity required!");
        }
        return errors;
    }
}
