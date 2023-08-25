package com.shoppingms.analysisservice.validator;



import com.shoppingms.analysisservice.dto.AnalysisRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnalysisValidator {
    public static List<String> validate(AnalysisRequest request){
        List<String> errors= new ArrayList<>();

        if(Objects.isNull(request.getCodeProduct())){
            errors.add("code required!");
        }
        if(Objects.isNull(request.getPercentage())){
            errors.add("percentage required!");
        }
        return errors;
    }
}
