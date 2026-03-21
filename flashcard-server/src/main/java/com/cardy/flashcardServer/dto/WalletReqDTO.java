package com.cardy.flashcardServer.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletReqDTO {
    private BigDecimal amount;
    private String description;
}
