package com.mbooks.microservicebooks.controller;

import com.mbooks.microservicebooks.dao.CategoryDao;
import com.mbooks.microservicebooks.exceptions.NotFoundException;
import com.mbooks.microservicebooks.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * <h2>Controller for the model Category</h2>
 */
@RestController
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;
    /**
     * <p>Lists all categories</p>
     * @return list
     */
    @GetMapping(value="/Genres")
    public List<Category> listCategories() {
        return categoryDao.findAll();
    }
    /**
     * <h2>Not needed for user application => for employees application</h2>
     * <p>adds a new category in db</p>
     * @param category
     * @return responseEntity
     */
    @PostMapping(value = "/Genres")
    public ResponseEntity<Void> addCategory(@Valid @RequestBody Category category) {
        Category categoryAdded =  categoryDao.save(category);
        if (categoryAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    //TODO details = list of books?
    /**
     * <p>show details of a particular category by its id</p>
     * @param id
     * @return the category
     */
    @GetMapping(value = "/Genres/{id}")
    public Optional<Category> showCategory(@PathVariable Integer id) {
        Optional<Category> category = categoryDao.findById(id);
        if(!category.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        return category;
    }
}
