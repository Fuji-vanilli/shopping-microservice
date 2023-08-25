package com.shoppingms.analysisservice.dto;

import com.shoppingms.analysisservice.model.Analysis;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnalysisMapperImpl implements AnalysisMapper{

    @Override
    public Analysis mapToAnalysis(AnalysisRequest request) {
        return Analysis.builder()
                .codeProduct(request.getCodeProduct())
                .percentage(request.getPercentage())
                .build();
    }

    @Override
    public AnalysisResponse mapToAnalysisResponse(Analysis analysis) {
        return AnalysisResponse.builder()
                .id(analysis.getId())
                .codeProduct(analysis.getCodeProduct())
                .percentage(analysis.getPercentage())
                .product(analysis.getProduct())
                .build();
    }
}
