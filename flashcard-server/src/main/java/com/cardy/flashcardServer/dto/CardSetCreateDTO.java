package com.cardy.flashcardServer.dto;

import com.cardy.flashcardServer.domain.CardSet;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardSetCreateDTO {
    private String title;
    private String description;
    private Double price;
    private List<CardSet.Card> cards;
}
