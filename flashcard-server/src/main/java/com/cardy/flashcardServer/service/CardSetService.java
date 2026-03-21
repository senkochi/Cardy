package com.cardy.flashcardServer.service;

import com.cardy.flashcardServer.client.WalletClient;
import com.cardy.flashcardServer.dto.CardSetCreateReqDTO;
import com.cardy.flashcardServer.dto.CardSetResDTO;
import com.cardy.flashcardServer.dto.WalletReqDTO;
import com.cardy.flashcardServer.entity.CardSet;
import com.cardy.flashcardServer.entity.Purchase;
import com.cardy.flashcardServer.mapper.CardSetMapper;
import com.cardy.flashcardServer.repository.CardSetRepository;
import com.cardy.flashcardServer.repository.PurchaseRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardSetService {

    @Autowired
    private CardSetRepository cardSetRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private WalletClient walletClient;

    @Autowired
    private CardSetMapper mapper;

    public CardSetResDTO create(CardSetCreateReqDTO req, String authorId){
        CardSet cardSet = mapper.toEntity(req);
        cardSet.setAuthorId(authorId);
        CardSet res = cardSetRepository.save(cardSet);
        return mapper.toDto(res);
    }

    public List<CardSetResDTO> getAll(){
        return mapper.toDtoList(cardSetRepository.findAll());
    }

    public List<CardSetResDTO> getByAuthor(String authorId){
        return mapper.toDtoList(cardSetRepository.findByAuthorId(authorId));
    }

    public CardSetResDTO getById(String id){
        return mapper.toDto(cardSetRepository.findById(id).orElseThrow());
    }

    public List<CardSetResDTO> getByKeyword(String keyword){
        return mapper.toDtoList(cardSetRepository.findByTitleContainingIgnoreCase(keyword));
    }

    public void buy(String cardSetId, String userId, String token){
        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bộ thẻ"));

        if(purchaseRepository.existsByUserIdAndCardSetId(userId, cardSetId)){
            throw new IllegalStateException("Bạn đã sở hữu bộ thẻ này rồi");
        }

        WalletReqDTO req = new WalletReqDTO(cardSet.getPrice(), cardSet.getTitle());
        String fullToken = "Bearer " + token;

        try{
            walletClient.withdraw(fullToken, req);
        } catch (FeignException ex){
            System.err.println("Status code từ Wallet: " + ex.status());
            // In toàn bộ Body mà Wallet gửi sang
            System.err.println("Body lỗi từ Wallet: " + ex.contentUTF8());
            ex.printStackTrace();
            throw new RuntimeException("Giao dịch không hợp lệ hoặc lỗi hệ thống");
        }

        try{
            Purchase purchase = Purchase.builder()
                    .userId(userId)
                    .cardSetId(cardSetId)
                    .purchasePrice(cardSet.getPrice())
                    .build();
            purchaseRepository.save(purchase);
        } catch (Exception ex){
            walletClient.deposit(token, new WalletReqDTO(cardSet.getPrice(), "Hoàn tiền lỗi hệ thống"));
            throw new RuntimeException("Lỗi lưu trữ, đã hoàn lại tiền vào ví cho bạn!");
        }
    }

}
