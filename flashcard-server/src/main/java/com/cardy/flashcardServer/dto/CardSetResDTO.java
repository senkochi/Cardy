package com.cardy.flashcardServer.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardSetResDTO {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer totalCards;
}
