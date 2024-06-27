package com.ribaso.bookservice.core.domain.model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByIsbn(String isbn);
}
