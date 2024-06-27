package com.ribaso.bookservice.core.domain.service.interfaces;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ribaso.bookservice.core.domain.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByIsbn(String isbn);
}
