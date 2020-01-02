package com.mbooks.microservicebooks.controller;


import com.mbooks.microservicebooks.dao.BorrowingTypeDao;
import com.mbooks.microservicebooks.exceptions.NotFoundException;
import com.mbooks.microservicebooks.model.BorrowingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * <h2>Controller for the model BorrowingType</h2>
 */
@RestController
public class BorrowingTypeController {
    @Autowired
    private BorrowingTypeDao borrowingTypeDao;
    /**
     * <p>Lists all borrowing types</p>
     * @return list
     */
    @GetMapping(value="/TypesDePrets")
    public List<BorrowingType> listBorrowingTypes() {
        List<BorrowingType> borrowingTypes = borrowingTypeDao.findAll();
        if(borrowingTypes.isEmpty()) throw new NotFoundException("Aucun type de pret trouvé");
        return borrowingTypes;
    }
    /**
     * <h2>Not needed for user application => for employees application</h2>
     * <p>adds a new borrowingType in db</p>
     * @param borrowingType
     * @return responseEntity
     */
    @PostMapping(value = "/TypesDePrets")
    public ResponseEntity<BorrowingType> addBorrowingType(@Valid @RequestBody BorrowingType borrowingType) {
        BorrowingType borrowingAdded =  borrowingTypeDao.save(borrowingType);
        if (borrowingAdded == null) {
            return ResponseEntity.noContent().build();
        }
        return  new ResponseEntity<BorrowingType>(borrowingAdded, HttpStatus.CREATED);
    }
    /**
     * <p>show details of a particular borrowingType by its id</p>
     * @param id
     * @return the borrowing type
     */
    @GetMapping(value = "/TypesDePrets/{id}")
    public Optional<BorrowingType> showBorrowingType(@PathVariable Integer id) {
        Optional<BorrowingType> borrow = borrowingTypeDao.findById(id);
        if(!borrow.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        return borrow;
    }
}
