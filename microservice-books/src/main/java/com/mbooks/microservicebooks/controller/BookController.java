package com.mbooks.microservicebooks.controller;

import com.mbooks.microservicebooks.dao.BookDao;
import com.mbooks.microservicebooks.exceptions.NotFoundException;
import com.mbooks.microservicebooks.model.Book;
import com.mbooks.microservicebooks.utils.CheckDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * <h2>Controller for model Book</h2>
 */
@RestController
public class BookController {
    @Autowired
    private BookDao bookDao;

    /**
     * <p>Lists all books</p>
     * @return list
     */
    @GetMapping(value="/Livres")
    public List<Book> listBooks() {
        List<Book> books = bookDao.findAll();
        if(books.isEmpty()) throw new NotFoundException("Aucun livre n'est disponible");
        return books;
    }

    /**
     * <h2>Not needed for user application => for employees application</h2>
     * <p>adds a new book in db, checks if the date is valid before save</p>
     * @param book
     * @return responseEntity
     */
    @PostMapping(value = "/Livres")
    public ResponseEntity<Void> addBook(@Valid @RequestBody Book book) {
        String date = book.getReleaseDate();
        CheckDate.checkDate(date);

        Book bookAdded =  bookDao.save(book);
        if (bookAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * <p>shows details of a particular book by its id</p>
     * @param id
     * @return the book
     */
    @GetMapping(value = "/Livres/{id}")
    public Optional<Book> showBook(@PathVariable Integer id) {
        Optional<Book> book = bookDao.findById(id);
        if(!book.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        return book;
    }
}
