package com.ribaso.bookservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookService;
import com.ribaso.bookservice.port.book.exceptions.BookNotFoundException;
import com.ribaso.bookservice.port.controller.BookController;
import com.ribaso.bookservice.service.MessagePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private MessagePublisher messagePublisher;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("1", "Title", "Subtitle", "123456789", "Abstract", "Author", "Publisher", "Price", 100);
    }

    @Test
    void getBook_ShouldReturnBook_WhenBookExists() throws Exception {
        when(bookService.getBook("1")).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    void getBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        when(bookService.getBook("1")).thenThrow(new BookNotFoundException("Book with ID 1 not found."));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBook_ShouldAddBook_WhenValidRequest() throws Exception {
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated());

        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    void updateBook_ShouldUpdateBook_WhenBookExists() throws Exception {
        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).updateBook(eq(book.getId()), any(Book.class));
    }

    @Test
    void updateBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        doThrow(new BookNotFoundException("Book with ID 1 not found.")).when(bookService).updateBook(eq("1"), any(Book.class));

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeBook_ShouldRemoveBook_WhenBookExists() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).removeBook("1");
    }

    @Test
    void removeBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        doThrow(new BookNotFoundException("Book with ID 1 not found.")).when(bookService).removeBook("1");

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBooks_ShouldReturnListOfBooks() throws Exception {
        List<Book> books = Arrays.asList(book);
        when(bookService.getBooks(1)).thenReturn(books);

        mockMvc.perform(get("/books?amount=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(book.getId()));
    }
}
