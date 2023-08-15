package com.shoppingms.productservice.validator;


import com.shoppingms.productservice.dto.ProductRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductValidator {
    public static List<String> validate(ProductRequest request){
        List<String> errors= new ArrayList<>();

        if(Objects.isNull(request.getCode())){
            errors.add("code required!");
        }
        if(Objects.isNull(request.getName())){
            errors.add("name required!");
        }
        if(Objects.isNull(request.getPrice())){
            errors.add("price required!");
        }
        return errors;
    }
}
