package com.ribaso.bookservice.core.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String id;
    private String title;
    private String subtitle;
    private String isbn;
    @JsonProperty("abstract")
    private String abstractText;  
    private String author;
    private String publisher;
    private String price;
    private String cover;
    private int numPages;

    
}

