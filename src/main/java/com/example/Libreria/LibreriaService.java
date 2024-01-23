package com.example.Libreria;

import java.util.List;
import java.util.Optional;

public interface LibreriaService {
    List<Book> getAllBooks();
    Optional<Book> getBookByTitolo(String titolo);
    User registerUser(User user);
    void addBook(Book book);
}