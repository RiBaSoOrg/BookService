package com.ribaso.bookservice.port.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange bookExchange() {
        return new DirectExchange("bookExchange");
    }

    @Bean
    public Queue bookQueue() {
        return new Queue("bookQueue");
    }

    @Bean
    public Binding binding(Queue bookQueue, DirectExchange bookExchange) {
        return BindingBuilder.bind(bookQueue).to(bookExchange).with("bookRoutingKey");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
