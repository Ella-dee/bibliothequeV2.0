package com.mbooks.microservicebooks.controller;

import com.mbooks.microservicebooks.dao.AuthorDao;
import com.mbooks.microservicebooks.dao.BookDao;
import com.mbooks.microservicebooks.exceptions.NotFoundException;
import com.mbooks.microservicebooks.model.Author;
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
 * <h2>Controller for the model Author</h2>
 */
@RestController
public class AuthorController {
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private BookDao bookDao;
    /**
     * <p>Lists all authors</p>
     * @return list
     */
    @GetMapping(value="/Auteurs")
    public List<Author> listAuthors() {
        List<Author> authors = authorDao.findAll();
        if(authors.isEmpty()) throw new NotFoundException("Aucun auteur n'est enregistré");
        return authors;
    }
    /**
     * <h2>Not needed for user application => for employees application</h2>
     * <p>adds a new author in db, checks if the date is valid before save</p>
     * @param author
     * @return responseEntity
     */
    @PostMapping(value = "/Auteurs")
    public ResponseEntity<Void> addAuthor(@Valid @RequestBody Author author) {
        String date = author.getBirthDate();
        CheckDate.checkDate(date);

        Author authorAdded =  authorDao.save(author);
        if (authorAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * <p>shows details of a particular author by its id</p>
     * @param id
     * @return the author
     */
    @GetMapping(value = "/Auteurs/{id}")
    public Optional<Author> showAuthor(@PathVariable Integer id) {
        Optional<Author> author = authorDao.findById(id);
        if(!author.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        return author;
    }
}
