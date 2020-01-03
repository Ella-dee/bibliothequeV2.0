package com.mbooks.microservicebooks.controller;

import com.mbooks.microservicebooks.dao.BookDao;
import com.mbooks.microservicebooks.dao.BookService;
import com.mbooks.microservicebooks.dao.BorrowingDao;
import com.mbooks.microservicebooks.exceptions.NotFoundException;
import com.mbooks.microservicebooks.model.Book;
import com.mbooks.microservicebooks.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h2>Controller for model Book</h2>
 */
@RestController
public class BookController {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BorrowingDao borrowingDao;
    @Autowired
    private BookService bookService;

    /**
     * <p>Lists all books</p>
     * @return list
     */
    @GetMapping(value="/Livres")
    public List<Book> listBooks() {
        List<Book> books = bookDao.findAll();
        if(books.isEmpty()) throw new NotFoundException("Aucun livre n'est disponible");
        for (Book b: books){
            bookService.setBookAvailability(b);
            bookDao.save(b);
        }
        return books;
    }


    /**
     * <p>Lists all book titles</p>
     * @return list
     */
    @GetMapping(value="/Titres")
    public List<String> listBookTitles() {
        List<Book> books = bookDao.findAll();
        List<String> bookTitles = new ArrayList<>();
        if(books.isEmpty()) throw new NotFoundException("Aucun livre n'est disponible");
        for (Book b: books) {
            bookTitles.add(b.getTitle());
        }
        return bookTitles;
    }


    /**
     * <p>Lists all books by titre like %</p>
     * @return list
     */
    @PostMapping("/Livres/rechercheParTitre")
    public List<Book> searchBooksByTitle(String title){
        List<Book> books = bookDao.findBooksByTitleContainingIgnoreCase(title);
        for (Book b:books){
            bookService.setBookAvailability(b);
        }
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
        DateUtils.checkDate(date);

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
    public Book showBook(@PathVariable Integer id) {
        //search for Optional not to get error
        Optional<Book> bookSearch = bookDao.findById(id);
        if(!bookSearch.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        //if optional is found, search for object book
        else {
            Book book = bookDao.findBookById(id);
            bookService.setBookAvailability(book);
            book.setUsersWaiting(book.getWaitingList().size());
            return book;
        }
    }
}
