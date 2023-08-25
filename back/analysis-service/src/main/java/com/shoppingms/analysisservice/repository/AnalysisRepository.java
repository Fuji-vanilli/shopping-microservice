package com.shoppingms.analysisservice.repository;

import com.shoppingms.analysisservice.model.Analysis;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnalysisRepository extends MongoRepository<Analysis, String> {
}
