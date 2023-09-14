package com.shoppingms.billingservice.validator;


import com.shoppingms.billingservice.dto.BillRequest;
import com.shoppingms.billingservice.dto.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillValidator {
    public static List<String> validate(BillRequest request){
        List<String> errors= new ArrayList<>();

        if(Objects.isNull(request.getCode())){
            errors.add("code required!");
        }
        if(Objects.isNull(request.getCodeOrder())){
            errors.add("code order required!");
        }
        if(Objects.isNull(request.getEmailClient())){
            errors.add("code order required!");
        }
        return errors;
    }
}
