package com.ribaso.bookservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        book = new Book("1", "Title", "Subtitle", "123456789", "Abstract", "Author", "Publisher", "Price", 100);
        bookRepository.save(book);
    }

    @Test
    void getBook_ShouldReturnBook_WhenBookExists() throws Exception {
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    void getBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBook_ShouldAddBook_WhenValidRequest() throws Exception {
        Book newBook = new Book("2", "New Title", "New Subtitle", "987654321", "New Abstract", "New Author", "New Publisher", "New Price", 200);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isCreated());

        Optional<Book> foundBook = bookRepository.findById("2");
        assert(foundBook.isPresent());
        assert(foundBook.get().getTitle().equals(newBook.getTitle()));
    }

    @Test
    void updateBook_ShouldUpdateBook_WhenBookExists() throws Exception {
        book.setTitle("Updated Title");

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNoContent());

        Optional<Book> updatedBook = bookRepository.findById("1");
        assert(updatedBook.isPresent());
        assert(updatedBook.get().getTitle().equals("Updated Title"));
    }

    @Test
    void updateBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        Book newBook = new Book("999", "Non-Existing Book", "Subtitle", "123456789", "Abstract", "Author", "Publisher", "Price", 100);

        mockMvc.perform(put("/books/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeBook_ShouldRemoveBook_WhenBookExists() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());

        Optional<Book> deletedBook = bookRepository.findById("1");
        assert(deletedBook.isEmpty());
    }

    @Test
    void removeBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        mockMvc.perform(delete("/books/999"))
                .andExpect(status().isNotFound());
    }



    @Test
    void getBooks_ShouldReturnListOfBooks() throws Exception {
        mockMvc.perform(get("/books?amount=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(book.getId()));
    }
}
