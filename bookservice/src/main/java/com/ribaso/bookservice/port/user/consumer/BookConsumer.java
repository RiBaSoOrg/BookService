package com.ribaso.bookservice.port.user.consumer;
import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookService;
import com.ribaso.bookservice.port.exceptions.BookNotFoundException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BookConsumer {

     private final BookService bookService;

    public BookConsumer(BookService bookService) {
        this.bookService = bookService;
    }

    @RabbitListener(queues = "bookQueue")
    public Book getBookDetails(String bookId) throws BookNotFoundException {
        return bookService.getBook(bookId);
    }
}
