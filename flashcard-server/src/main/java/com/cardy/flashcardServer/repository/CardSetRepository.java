package com.cardy.flashcardServer.repository;

import com.cardy.flashcardServer.entity.CardSet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CardSetRepository extends MongoRepository<CardSet, String> {
    List<CardSet> findByAuthorId(String authorId);
    List<CardSet> findByPrice(Double price);
    List<CardSet> findByTitleContainingIgnoreCase(String keyword);

    @Override
    Optional<CardSet> findById(String s);
}
