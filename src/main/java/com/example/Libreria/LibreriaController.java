package com.example.Libreria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibreriaController {

    @Autowired
    private LibreriaService libraryService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/registrazione")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registrazione";
    }

    @PostMapping("/registrazione")
    public String registerUser(@ModelAttribute User user, Model model) {
        libraryService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/aggiungiLibro")
    public String addBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "aggiungiLibro";
    }

    @PostMapping("/aggiungiLibro")
    public String addBook(@ModelAttribute Book book, Model model) {
        libraryService.addBook(book);
        return "redirect:/listaLibri";
    }

    @GetMapping("/listaLibri")
    public String listBooks(Model model) {
        List<Book> books = libraryService.getAllBooks();
        model.addAttribute("books", books);
        return "listaLibri";
    }

    @GetMapping("/dettaglioLibro")
    public String bookDetails(@RequestParam String titolo, Model model) {
        Book book = libraryService.getBookByTitolo(titolo).orElse(null);
        model.addAttribute("book", book);
        return "dettaglioLibro";
    }
}
