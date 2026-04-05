package com.cardy.flashcardServer.mapper;

import com.cardy.flashcardServer.dto.CardSetCreateDTO;
import com.cardy.flashcardServer.dto.CardSetDTO;
import com.cardy.flashcardServer.domain.CardSet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardSetMapper {
    @Mapping(target = "totalCards", expression = "java(cardSet.getCards() != null ? cardSet.getCards().size() : 0)")
    CardSetDTO toDto(CardSet cardSet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    CardSet toEntity(CardSetCreateDTO cardSetCreateDTO);

    List<CardSetDTO> toDtoList(List<CardSet> cardSets);
}
