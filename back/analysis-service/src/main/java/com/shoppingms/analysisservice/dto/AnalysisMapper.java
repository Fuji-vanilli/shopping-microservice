package com.shoppingms.analysisservice.dto;

import com.shoppingms.analysisservice.model.Analysis;

public interface AnalysisMapper {
    Analysis mapToAnalysis(AnalysisRequest request);
    AnalysisResponse mapToAnalysisResponse(Analysis analysis);
}
