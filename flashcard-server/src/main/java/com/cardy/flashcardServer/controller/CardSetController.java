package com.cardy.flashcardServer.controller;

import com.cardy.flashcardServer.dto.CardSetCreateReqDTO;
import com.cardy.flashcardServer.dto.CardSetResDTO;
import com.cardy.flashcardServer.dto.WalletReqDTO;
import com.cardy.flashcardServer.service.CardSetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
public class CardSetController {
    @Autowired
    private CardSetService cardSetService;

    @PostMapping
    public ResponseEntity<CardSetResDTO> create(
            @Valid @RequestBody CardSetCreateReqDTO req,
            @AuthenticationPrincipal Jwt jwt){
        String authorId = jwt.getSubject();
        CardSetResDTO cardSet = cardSetService.create(req, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardSet);
    }

    @GetMapping("/market")
    public List<CardSetResDTO> getMarket(){
        return cardSetService.getAll();
    }

    @GetMapping("/author/{userId}")
    public List<CardSetResDTO> getByAuthor(@PathVariable String userId){
        return cardSetService.getByAuthor(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardSetResDTO> getById(@PathVariable String id){
        return ResponseEntity.ok(cardSetService.getById(id));
    }

    @GetMapping("/search")
    public List<CardSetResDTO> search(@RequestParam String key) {
        return cardSetService.getByKeyword(key);
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buy(
            @RequestParam String id,
            @AuthenticationPrincipal Jwt jwt
    ){
        String userId = jwt.getClaim("userId");
        cardSetService.buy(id, userId, jwt.getTokenValue());
        return ResponseEntity.ok("Đã mua thẻ thành công");
    }
}
