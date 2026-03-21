package com.cardy.flashcardServer.dto;

import com.cardy.flashcardServer.entity.CardSet;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardSetCreateReqDTO {
    private String title;
    private String description;
    private Double price;
    private List<CardSet.Card> cards;
}
