package com.ribaso.bookservice.port.user.consumer;

import com.ribaso.bookservice.core.domain.model.Book;
import com.ribaso.bookservice.core.domain.service.interfaces.BookRepository;
import com.ribaso.bookservice.port.exceptions.BookNotFoundException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookConsumer {

    @Autowired
    private BookRepository bookRepository;

    @RabbitListener(queues = "bookQueue")
    public void receiveMessage(String message) throws BookNotFoundException {
        // Nachricht parsen, um Book ID zu extrahieren
        String[] parts = message.split(",");
        String bookID = parts[0];

        // Book aus der Datenbank laden oder neu erstellen
        Book book = bookRepository.findById(bookID)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        // Book verarbeiten (hier können Sie die Logik hinzufügen)
    }
}
