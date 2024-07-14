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
    @SendTo
    public Book handleBookRequest(String bookId) throws BookNotFoundException {
        Book book = bookService.getBook(bookId);
        log.info("Received and responding with book ID: {}, Book Title: {}", bookId, book.getTitle());
        return book; // Die Antwort wird automatisch an die `replyTo`-Queue gesendet
    }
}
