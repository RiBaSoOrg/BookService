package com.ribaso.bookservice.port.user.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookService;
import com.ribaso.bookservice.port.exceptions.BookNotFoundException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class BookConsumer {
   
    @Autowired
    private final BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    public BookConsumer(BookService bookService) {
        this.bookService = bookService;
    }

    @RabbitListener(queues = "bookQueue")
    @SendTo
    public String getBookDetails(String bookId) throws BookNotFoundException {
        Book book = bookService.getBook(bookId);
        try {
            return objectMapper.writeValueAsString(book);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize book details", e);
        }
    }
}
