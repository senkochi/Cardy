package com.cardy.flashcardServer.repository;

import com.cardy.flashcardServer.entity.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {
    boolean existsByUserIdAndCardSetId(String userId, String cardSetId);

    List<Purchase> findByUserId(String userId);
}
