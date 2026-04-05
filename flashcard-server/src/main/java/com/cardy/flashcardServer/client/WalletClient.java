package com.cardy.flashcardServer.client;

import com.cardy.flashcardServer.dto.WalletDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "wallet-service")
public interface WalletClient {
    @PostMapping("/api/wallet/withdraw")
    Map<String, String> withdraw(@RequestHeader("Authorization") String token,
                                 @RequestBody WalletDTO req);
    @PostMapping("/api/wallet/deposit")
    ResponseEntity<?> deposit(@RequestHeader("Authorization") String token,
                              @RequestBody WalletDTO req);
}
