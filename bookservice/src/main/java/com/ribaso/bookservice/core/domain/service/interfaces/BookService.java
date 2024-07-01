package com.ribaso.bookservice.core.domain.service.interfaces;

import java.util.List;
import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.port.book.exceptions.BookAlreadyExistsException;
import com.ribaso.bookservice.port.book.exceptions.BookNotFoundException;

public interface BookService {

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param bookID the unique identifier of the book
     * @return the book with the specified ID
     * @throws BookNotFoundException if no book with the given ID is found
     */
    Book getBook(String bookID) throws BookNotFoundException;

    /**
     * Adds a new book to the collection.
     *
     * @param book the book to add
     * @throws BookAlreadyExistsException if a book with the same ID already exists
     */
    void addBook(Book book) throws BookAlreadyExistsException;

    /**
     * Updates an existing book.
     *
     * @param bookID the unique identifier of the book to update
     * @param book   the book details to update
     * @throws BookNotFoundException if no book with the given ID is found
     */
    void updateBook(String bookID, Book book) throws BookNotFoundException;

    /**
     * Removes a book by its unique identifier.
     *
     * @param bookID the unique identifier of the book to remove
     * @throws BookNotFoundException if no book with the given ID is found
     */
    void removeBook(String bookID) throws BookNotFoundException;

    /**
     * Retrieves the unique identifier of a book by its ISBN.
     *
     * @param isbn the ISBN of the book
     * @return the unique identifier of the book
     * @throws BookNotFoundException if no book with the given ISBN is found
     */
    String getBookID(String isbn) throws BookNotFoundException;

    /**
     * Retrieves a list of books.
     *
     * @param amount the maximum number of books to retrieve
     * @return a list of books
     */
    List<Book> getBooks(int amount);
}
