package com.ribaso.bookservice.core.domain.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.model.BookDTO;
import com.ribaso.bookservice.core.domain.service.interfaces.BookRepository;
import com.ribaso.bookservice.core.domain.service.interfaces.BookService;
import com.ribaso.bookservice.port.exceptions.BookAlreadyExistsException;
import com.ribaso.bookservice.port.exceptions.BookNotFoundException;
import com.ribaso.bookservice.port.integration.BookClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookClient bookClient;

    @Override
    public Book getBook(String bookID) throws BookNotFoundException {
        return bookRepository.findById(bookID)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + bookID + " not found."));
    }

    @Override
    public void addBook(Book book) throws BookAlreadyExistsException {
        if (bookRepository.existsById(book.getId())) {
            throw new BookAlreadyExistsException("Book with ID " + book.getId() + " already exists.");
        }
        bookRepository.save(book);
    }

    @Override
    public void updateBook(String bookID, Book book) throws BookNotFoundException {
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
    public String getBookID(String isbn) throws BookNotFoundException {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }
        return book.getId();
    }

    @Override
    public List<Book> getBooks(int amount) {
        List<Book> books = bookRepository.findAll();
        if (amount > 0) {
            return books.stream().limit(amount).collect(Collectors.toList());
        }
        return books;
    }

    public void syncBooksFromExternalApi() {
        List<BookDTO> books = bookClient.fetchBooks();
        books.stream()
             .map(this::convertToEntity)
             .forEach(this::saveOrUpdateBook);
    }

    public Book convertToEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setSubtitle(dto.getSubtitle());
        book.setIsbn(dto.getIsbn());  
        book.setAbstractText(dto.getAbstractText()); 
        book.setAuthor(dto.getAuthor());
        book.setPublisher(dto.getPublisher());
        book.setPrice(dto.getPrice());
        book.setNumPages(dto.getNumPages()); 
        return book;
    }

    private void saveOrUpdateBook(Book book) {
        bookRepository.findById(book.getId())
                      .ifPresentOrElse(
                          existingBook -> {
                            try {
                                updateBook(existingBook.getId(), book);
                            } catch (BookNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        },
                          () -> {
                            try {
                                addBook(book);
                            } catch (BookAlreadyExistsException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                      );
    }

    /*@Scheduled(fixedRate = 10000) // Zeit in Millisekunden
    public void scheduledUpdate() {
        syncBooksFromExternalApi();
}*/
}