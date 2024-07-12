package com.ribaso.bookservice.port.user.controller;

import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookService;
import com.ribaso.bookservice.port.exceptions.BookAlreadyExistsException;
import com.ribaso.bookservice.port.exceptions.BookNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

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
    @Operation(summary = "Retrieve books", description = "Retrieves a list of books. If 'amount' is set to 0, all books will be returned.")
    public ResponseEntity<List<Book>> getBooks(@RequestParam (required = true)
    @Parameter(description = "The maximum number of books to retrieve. Set to 0 to retrieve all books.")
    int amount) {
        
        return ResponseEntity.ok(bookService.getBooks(amount));
    }
}
