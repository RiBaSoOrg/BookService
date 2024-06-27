package com.ribaso.bookservice.core.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.model.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getBook(String bookID) {
        return bookRepository.findById(bookID).orElse(null);
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void updateBook(String bookID, Book book) {
        bookRepository.save(book);
    }

    @Override
    public void removeBook(String bookID) {
        bookRepository.deleteById(bookID);
    }

    @Override
    public String getBookID(String isbn) {
        return bookRepository.findByIsbn(isbn).getId();
    }

    @Override
    public List<Book> getBooks(int amount) {
        // maybe adjust 
        return bookRepository.findAll().stream().limit(amount).collect(Collectors.toList());
    }
}