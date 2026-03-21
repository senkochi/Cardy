package com.cardy.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("identity-server", r -> r.path("/api/auth/**")
                        .uri("http://localhost:9000"))

                .route("login", r -> r.path("/logout")
                        .uri("http://localhost:9000"))

                .route("flashcard-server", r -> r.path("/api/flashcards/**")
                        .uri("http://localhost:8081"))

                .route("wallet-server", r -> r.path("/api/wallet/**")
                        .uri("http://localhost:8082"))
                .build();
    }
}
