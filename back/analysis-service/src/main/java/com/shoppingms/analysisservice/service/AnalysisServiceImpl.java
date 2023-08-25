package com.shoppingms.analysisservice.service;

import com.shoppingms.analysisservice.dto.AnalysisMapper;
import com.shoppingms.analysisservice.dto.AnalysisRequest;
import com.shoppingms.analysisservice.model.Analysis;
import com.shoppingms.analysisservice.repository.AnalysisRepository;
import com.shoppingms.analysisservice.utils.Response;
import com.shoppingms.analysisservice.webClient.WebClientProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AnalysisServiceImpl implements AnalysisService{
    private final AnalysisRepository analysisRepository;
    private final AnalysisMapper analysisMapper;
    private final WebClientProduct webClient;
    @Override
    public Response add(AnalysisRequest request) {
        Analysis analysis= analysisMapper.mapToAnalysis(request);

        analysis.setProduct(webClient.getProduct(request.getCodeProduct()));

        analysisRepository.save(analysis);
        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "analysis", analysisMapper.mapToAnalysisResponse(analysis)
                ),
                "new analysis added"
        );
    }

    @Override
    public Response get(String code) {
        return null;
    }

    @Override
    public Response all() {
        return null;
    }

    @Override
    public Response delete(String code) {
        return null;
    }
    private Response generateResponse(HttpStatus status, URI location, Map<?, ?> data, String message) {
        return Response.builder()
                .timeStamp(LocalDateTime.now())
                .status(status)
                .statusCode(status.value())
                .data(data)
                .location(location)
                .message(message)
                .build();
    }
}
