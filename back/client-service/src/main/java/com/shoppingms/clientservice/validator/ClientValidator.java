package com.shoppingms.clientservice.validator;


import com.shoppingms.clientservice.dto.ClientRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientValidator {
    public static List<String> validate(ClientRequest request){
        List<String> errors= new ArrayList<>();

        if(Objects.isNull(request.getCode())){
            errors.add("code required!");
        }
        if(Objects.isNull(request.getAdress())){
            errors.add("adress required!");
        }
        if(Objects.isNull(request.getFirstname())){
            errors.add("firstname required!");
        }
        if(Objects.isNull(request.getEmail())){
            errors.add("email required!");
        }
        if(Objects.isNull(request.getLastname())){
            errors.add("lastname required!");
        }
        return errors;
    }
}
