package com.ribaso.bookservice.core.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookRepository;
import com.ribaso.bookservice.core.domain.service.interfaces.BookService;
import com.ribaso.bookservice.port.book.exceptions.BookAlreadyExistsException;
import com.ribaso.bookservice.port.book.exceptions.BookNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getBook(String bookID) throws BookNotFoundException {
        return bookRepository.findById(bookID).orElseThrow(() -> new BookNotFoundException("Book with ID " + bookID + " not found."));
    }

    @Override
    public void addBook(Book book) throws BookAlreadyExistsException {
        if (bookRepository.existsById(book.getId())) {
            throw new BookAlreadyExistsException("Book with ID " + book.getId() + " already exists.");
        }
        bookRepository.save(book);
    }

    @Override
    public void updateBook(String bookID, Book book)throws BookNotFoundException {
        if (!bookRepository.existsById(bookID)) {
            throw new BookNotFoundException("Book with ID " + bookID + " not found.");
        }
        bookRepository.save(book);
    }

    @Override
    public void removeBook(String bookID) throws BookNotFoundException {
        if (!bookRepository.existsById(bookID)) {
            throw new BookNotFoundException("Book with ID " + bookID + " not found.");
        }
        bookRepository.deleteById(bookID);
    }

    @Override
    public String getBookID(String isbn) throws BookNotFoundException{
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }
        return book.getId();
    }

    @Override
    public List<Book> getBooks(int amount) {
        // maybe adjust 
        return bookRepository.findAll().stream().limit(amount).collect(Collectors.toList());
    }
}