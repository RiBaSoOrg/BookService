package com.ribaso.bookservice.core.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book  {
    @Id
    private String id;
    private String title;
    private String subtitle;
    private String isbn;
    private String abstractText;  
    private String author;
    private String publisher;
    private String price;
    private String cover;
    private int numPages;
}
