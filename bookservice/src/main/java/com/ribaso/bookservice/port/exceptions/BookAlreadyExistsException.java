package com.ribaso.bookservice.port.exceptions;
/**
 * Exception thrown when a book already exists.
 */
public class BookAlreadyExistsException extends Exception  {
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
