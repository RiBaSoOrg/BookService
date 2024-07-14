package com.ribaso.bookservice.port.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ribaso.bookservice.core.domain.model.BookDTO;



@Service
public class BookClient {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public BookClient(@Value("${bookmonkey.api.url}") String apiUrl, RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        this.apiUrl = apiUrl;
    }

    public List<BookDTO> fetchBooks() {
        ResponseEntity<List<BookDTO>> response = restTemplate.exchange(
            apiUrl + "/books",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<BookDTO>>() {}
        );
        return response.getBody();
    }
}