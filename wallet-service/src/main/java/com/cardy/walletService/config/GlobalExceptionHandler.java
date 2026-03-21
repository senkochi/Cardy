package com.cardy.walletService.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleBalanceError(IllegalStateException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "IllegalStateException");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception e) {
        e.printStackTrace(); // In lỗi đỏ lòm ra Console để bạn copy cho mình xem
        Map<String, Object> body = new HashMap<>();
        body.put("message", e.getMessage());
        body.put("type", e.getClass().getSimpleName());
        return ResponseEntity.status(500).body(body);
    }
}
