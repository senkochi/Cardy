package com.cardy.learningServer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    public static final String LEARN_FANOUT_EXCHANGE = "learn.exchange";
    public static final String LEARN_QUEUE = "learn.queue";

    @Bean
    public FanoutExchange learnFanoutExchange() { return new FanoutExchange(LEARN_FANOUT_EXCHANGE); }

    @Bean
    public Queue learnQueue() { return new Queue(LEARN_QUEUE); }

    @Bean
    public Binding learnBinding(Queue learnQueue, FanoutExchange learnFanoutExchange) {
        return BindingBuilder.bind(learnQueue).to(learnFanoutExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() { return new JacksonJsonMessageConverter(); }
}
