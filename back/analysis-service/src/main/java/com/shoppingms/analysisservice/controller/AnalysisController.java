package com.shoppingms.analysisservice.controller;

import com.shoppingms.analysisservice.dto.AnalysisRequest;
import com.shoppingms.analysisservice.utils.Response;
import jakarta.ws.rs.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AnalysisController {
    @PostMapping("add")
    ResponseEntity<Response> add(@RequestBody AnalysisRequest request);
    @GetMapping("get/{code}")
    ResponseEntity<Response> get(@PathVariable String code);
    @GetMapping("all")
    ResponseEntity<Response> all();
    @DeleteMapping("delete/{code}")
    ResponseEntity<Response> delete(@PathVariable String code);
}
