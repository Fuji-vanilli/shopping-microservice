package com.shoppingms.inventoryservice.validator;


import com.shoppingms.inventoryservice.dto.InventoryRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryValidator {
    public static List<String> validate(InventoryRequest request){
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
