package com.example.Libreria;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {

    @Autowired
    private LibreriaService libraryService;

    @GetMapping("/api/libri")
    public List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/api/libro")
    public ResponseEntity<Book> getBookByTitolo(@RequestParam String titolo) {
        Optional<Book> book = libraryService.getBookByTitolo(titolo);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/sincronizza")
    public void synchronizeWithGoogleApis() {
    	String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=spring%20framework";

        WebClient webClient = WebClient.create();
        Book book = webClient
                .get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(GoogleBooksResponse.class)
                .block();

        if (googleBooksResponse != null && googleBooksResponse.getItems() != null) {
            for (GoogleBookItem item : googleBooksResponse.getItems()) {
                Book book = new Book();
                book.setTitolo(item.getVolumeInfo().getTitle());
                book.setAutore(item.getVolumeInfo().getAuthors() != null ? String.join(", ", item.getVolumeInfo().getAuthors()) : "");
                book.setAnnoPubblicazione(item.getVolumeInfo().getPublishedDate());
                book.setPrezzo(0.0);

                libraryService.addBook(book);
            }
        }
    }
}