package com.ribaso.bookservice.port.book.exceptions;
/**
 * Exception thrown when a book is not found.
 */
public class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}
