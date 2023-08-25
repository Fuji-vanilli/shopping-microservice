package com.shoppingms.analysisservice.controller;

import com.shoppingms.analysisservice.dto.AnalysisRequest;
import com.shoppingms.analysisservice.service.AnalysisService;
import com.shoppingms.analysisservice.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shoppingms.analysisservice.utils.Root.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT)
@RequiredArgsConstructor
public class AnalysisApi implements AnalysisController{
    private final AnalysisService analysisService;
    @Override
    public ResponseEntity<Response> add(AnalysisRequest request) {
        return ResponseEntity.ok(analysisService.add(request));
    }

    @Override
    public ResponseEntity<Response> get(String code) {
        return ResponseEntity.ok(analysisService.get(code));
    }

    @Override
    public ResponseEntity<Response> all() {
        return ResponseEntity.ok(analysisService.all());
    }

    @Override
    public ResponseEntity<Response> delete(String code) {
        return ResponseEntity.ok(analysisService.delete(code));
    }
}
