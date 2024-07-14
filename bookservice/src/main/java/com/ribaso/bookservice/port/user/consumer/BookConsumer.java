package com.ribaso.bookservice.port.user.consumer;

import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookService;
import com.ribaso.bookservice.port.exceptions.BookNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class BookConsumer {

    private static final Logger log = LoggerFactory.getLogger(Book.class);
   
    @Autowired
    private final BookService bookService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public BookConsumer(BookService bookService) {
        this.bookService = bookService;
    }

    @RabbitListener(queues = "bookQueue")
    public void getBookDetails(String bookId, @Header("correlation_id") String correlationId) throws BookNotFoundException {
        Book book = bookService.getBook(bookId);
        log.info("Received book: " + book.getTitle());
        log.info("Sending book details for ID: {} with correlationId: {}", bookId, correlationId);
    
        try {
            rabbitTemplate.convertAndSend(
                "bookExchange",
                "bookResponseRoutingKey",
                book,
                message -> {
                    MessageProperties properties = message.getMessageProperties();
                    properties.setCorrelationId(correlationId);
                    return message;
                }
            );
            log.info("Book sent: " + book.getTitle() + " with correlation ID: " + correlationId);
        } catch (Exception e) {
            log.error("Failed to send book details", e);
            throw new RuntimeException("Failed to send book details", e);
        }
    }
}
