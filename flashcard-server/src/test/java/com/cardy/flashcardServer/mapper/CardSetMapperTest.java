package com.cardy.flashcardServer.mapper;

import com.cardy.flashcardServer.dto.CardSetDTO;
import com.cardy.flashcardServer.domain.CardSet;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;

class CardSetMapperTest {
    private final CardSetMapper mapper = Mappers.getMapper(CardSetMapper.class);

    @Test
    void shouldMapEnityToResponseDTO(){
        CardSet entity = new CardSet();
        entity.setId("123");
        entity.setTitle("Tiếng Nhật N5");
        entity.setDescription("Tiếng Nhật cơ bản cho người mới");
        entity.setPrice(BigDecimal.valueOf(10));
        entity.setCards(Arrays.asList(new CardSet.Card("One", "Một"), new CardSet.Card("Two", "Hai")));

        CardSetDTO cardSetResDTO = mapper.toDto(entity);

        assertThat(cardSetResDTO).isNotNull();
        assertThat(cardSetResDTO.getTitle()).isEqualTo("Tiếng Nhật N5");
        assertThat(cardSetResDTO.getDescription()).isEqualTo("Tiếng Nhật cơ bản cho người mới");
        assertThat(cardSetResDTO.getPrice()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(cardSetResDTO.getTotalCards()).isEqualTo(2);
    }
}
