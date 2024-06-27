package com.ribaso.bookservice.core.domain.service;

import java.util.List;
import com.ribaso.bookservice.core.domain.model.Book;

public interface IBookService {
    Book getBook(String bookID);
    void addBook(Book book);
    void updateBook(String bookID, Book book);
    void removeBook(String bookID);
    String getBookID(String isbn);
    List<Book> getBooks(int amount);
}

    
