package com.shoppingms.analysisservice.service;

import com.shoppingms.analysisservice.dto.AnalysisRequest;
import com.shoppingms.analysisservice.utils.Response;

public interface AnalysisService {
    Response add(AnalysisRequest request);
    Response get(String code);
    Response all();
    Response delete(String code);
}
