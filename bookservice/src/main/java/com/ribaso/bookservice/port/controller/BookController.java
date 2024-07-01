package com.ribaso.bookservice.port.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookService;
import com.ribaso.bookservice.port.book.exceptions.BookAlreadyExistsException;
import com.ribaso.bookservice.port.book.exceptions.BookNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{bookID}")
    public ResponseEntity<Book> getBook(@PathVariable String bookID) throws BookNotFoundException {
        Book book = bookService.getBook(bookID);
        return (book != null) ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody Book book) throws BookAlreadyExistsException {
        bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{bookID}")
    public ResponseEntity<Void> updateBook(@PathVariable String bookID, @RequestBody Book book) throws BookNotFoundException {
        bookService.updateBook(bookID, book);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{bookID}")
    public ResponseEntity<Void> removeBook(@PathVariable String bookID) throws BookNotFoundException {
        bookService.removeBook(bookID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam int amount) {
        List<Book> books = bookService.getBooks(amount);
        return ResponseEntity.ok(books);
    }
}
