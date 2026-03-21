package com.cardy.flashcardServer.mapper;

import com.cardy.flashcardServer.dto.CardSetCreateReqDTO;
import com.cardy.flashcardServer.dto.CardSetResDTO;
import com.cardy.flashcardServer.entity.CardSet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardSetMapper {
    @Mapping(target = "totalCards", expression = "java(cardSet.getCards() != null ? cardSet.getCards().size() : 0)")
    CardSetResDTO toDto(CardSet cardSet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    CardSet toEntity(CardSetCreateReqDTO cardSetCreateReqDTO);

    List<CardSetResDTO> toDtoList(List<CardSet> cardSets);
}
