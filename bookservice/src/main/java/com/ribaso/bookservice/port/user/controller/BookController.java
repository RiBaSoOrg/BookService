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
    @Operation(summary = "Retrieve a book", description = "Retrieve a single book by its unique identifier.")
    public ResponseEntity<Book> getBook(@PathVariable 
    @Parameter(description = "The unique identifier of the book to retrieve.")
    
    String bookID) throws BookNotFoundException {
        return ResponseEntity.ok(bookService.getBook(bookID));
    }

    @PostMapping
    @Operation(summary = "Add a new book", description = "Adds a new book to the collection. If a book with the same ID already exists, an error is thrown.")
    public ResponseEntity<Void> addBook(@RequestBody 
    @Parameter(description = "The book object to add to the collection.", required = true)
    Book book) throws BookAlreadyExistsException {
        bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{bookID}")
    @Operation(summary = "Update a book", description = "Updates an existing book identified by its ID. If no book is found with the given ID, an error is thrown.")
    public ResponseEntity<Void> updateBook(@PathVariable 
    @Parameter(description = "The unique identifier of the book to update.")
    String bookID, 
    @RequestBody 
    @Parameter(description = "The updated book object.", required = true)
    Book book) throws BookNotFoundException {
        bookService.updateBook(bookID, book);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{bookID}")
    @Operation(summary = "Remove a book", description = "Removes a book from the collection based on its unique identifier. If no book is found with the given ID, an error is thrown.")
    public ResponseEntity<Void> removeBook(@PathVariable 
    @Parameter(description = "The unique identifier of the book to remove.")
    String bookID) throws BookNotFoundException {
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
