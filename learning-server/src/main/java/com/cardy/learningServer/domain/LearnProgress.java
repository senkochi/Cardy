package com.cardy.learningServer.domain;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "card_sets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearnProgress {
    @Id
    private String id;

    private String userId;
    private String cardSetId;

    private Double easinessFactor = 2.5;
    private int intervalDay = 0;
    private int repetitions = 0;

    private LocalDate nextReviewDate;
    private LocalDateTime lastUpdate;
}
