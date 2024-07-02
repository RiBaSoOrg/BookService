package com.ribaso.bookservice.port.user.controller;

import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookService;
import com.ribaso.bookservice.port.exceptions.BookAlreadyExistsException;
import com.ribaso.bookservice.port.exceptions.BookNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{bookID}")
    public ResponseEntity<Book> getBook(@PathVariable String bookID) throws BookNotFoundException {
        return ResponseEntity.ok(bookService.getBook(bookID));
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
        return ResponseEntity.ok(bookService.getBooks(amount));
    }
}
