package com.cardy.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalLoggingFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long endTime = System.currentTimeMillis();
            System.out.println("Request: " + exchange.getRequest().getPath() +
                    " | Status: " + exchange.getResponse().getStatusCode() +
                    " | Time: " + (endTime - startTime) + "ms");
        }));
    }
    @Override
    public int getOrder() { return -1; } // Ưu tiên chạy sớm nhất
}
