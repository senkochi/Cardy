package com.cardy.flashcardServer;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers // Tự động quản lý bật/tắt Docker
public abstract class BaseIntegrationTest {

    // Khai báo Container RabbitMQ
    @Container
    static final RabbitMQContainer rabbitMQ = new RabbitMQContainer("rabbitmq:3.12-management");

    // Khai báo Container MongoDB
    @Container
    static final MongoDBContainer mongoDB = new MongoDBContainer("mongo:6.0");

    // Đè cấu hình từ Docker vào Spring Context
    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQ::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQ::getAmqpPort);
        registry.add("spring.data.mongodb.uri", mongoDB::getReplicaSetUrl);
    }
}
