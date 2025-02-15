package com.ribaso.bookservice.port.user.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ribaso.bookservice.port.exceptions.BookAlreadyExistsException;
import com.ribaso.bookservice.port.exceptions.BookNotFoundException;
/**
 * Global exception handler for handling exceptions across the whole application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles BookNotFoundException and returns a 404 Not Found response.
     *
     * @param ex the exception that was thrown
     * @param request the current request
     * @return a ResponseEntity containing the exception message and a 404 Not Found status
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
     @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<?> handleBookAlreadyExistsException(BookAlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
