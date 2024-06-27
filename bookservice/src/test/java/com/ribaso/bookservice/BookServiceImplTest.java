package com.ribaso.bookservice;

import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.impl.BookServiceImpl;
import com.ribaso.bookservice.core.domain.service.interfaces.BookRepository;
import com.ribaso.bookservice.port.book.exceptions.BookNotFoundException;
import com.ribaso.bookservice.port.book.exceptions.BookAlreadyExistsException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book("1", "Title", "Subtitle", "123456789", "Abstract", "Author", "Publisher", "Price", 100);
    }

    @Test
    void getBook_ShouldReturnBook_WhenBookExists() throws BookNotFoundException {
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBook("1");

        assertNotNull(foundBook);
        assertEquals(book.getId(), foundBook.getId());
    }

    @Test
    void getBook_ShouldThrowBookNotFoundException_WhenBookDoesNotExist() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBook("1"));
    }

    @Test
    void addBook_ShouldAddBook_WhenBookDoesNotExist() throws BookAlreadyExistsException {
        when(bookRepository.existsById(book.getId())).thenReturn(false);

        bookService.addBook(book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void addBook_ShouldThrowBookAlreadyExistsException_WhenBookAlreadyExists() {
        when(bookRepository.existsById(book.getId())).thenReturn(true);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.addBook(book));
    }

    @Test
    void updateBook_ShouldUpdateBook_WhenBookExists() throws BookNotFoundException {
        when(bookRepository.existsById(book.getId())).thenReturn(true);

        bookService.updateBook(book.getId(), book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_ShouldThrowBookNotFoundException_WhenBookDoesNotExist() {
        when(bookRepository.existsById(book.getId())).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(book.getId(), book));
    }

    @Test
    void removeBook_ShouldRemoveBook_WhenBookExists() throws BookNotFoundException {
        when(bookRepository.existsById(book.getId())).thenReturn(true);

        bookService.removeBook(book.getId());

        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    void removeBook_ShouldThrowBookNotFoundException_WhenBookDoesNotExist() {
        when(bookRepository.existsById(book.getId())).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.removeBook(book.getId()));
    }

    @Test
    void getBookID_ShouldReturnBookID_WhenBookExists() throws BookNotFoundException {
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(book);

        String bookID = bookService.getBookID(book.getIsbn());

        assertEquals(book.getId(), bookID);
    }

    @Test
    void getBookID_ShouldThrowBookNotFoundException_WhenBookDoesNotExist() {
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> bookService.getBookID(book.getIsbn()));
    }

    @Test
    void getBooks_ShouldReturnListOfBooks() {
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> foundBooks = bookService.getBooks(1);

        assertEquals(1, foundBooks.size());
        assertEquals(book.getId(), foundBooks.get(0).getId());
    }
}