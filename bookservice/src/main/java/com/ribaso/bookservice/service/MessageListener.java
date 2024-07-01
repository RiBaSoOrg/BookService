package com.ribaso.bookservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.ribaso.bookservice.config.RabbitMQConfig;

@Service
public class MessageListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        // Hier kannst die Nachricht verarbeiten werden
    }
}
