package com.ribaso.bookservice.port.user.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookService;
import com.ribaso.bookservice.port.exceptions.BookNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
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
    public void getBookDetails(String bookId) throws BookNotFoundException {
        Book book = bookService.getBook(bookId);
        System.out.println(bookId);
        System.out.println("Received book: " + book.getTitle());
        try {
            rabbitTemplate.convertAndSend("bookExchange", "bookRoutingKey", book);
            log.info("Book send: " +book.getTitle());
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize book details", e);
        }
    }
}
