package com.cardy.learningServer.repository;

import com.cardy.learningServer.domain.LearnProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LearningRepository extends MongoRepository<LearnProgress, String> {
    List<LearnProgress> findByUserId(String userId);
    Optional<LearnProgress> findByUserIdAndCardSetId(String userId, String cardSetId);
}
