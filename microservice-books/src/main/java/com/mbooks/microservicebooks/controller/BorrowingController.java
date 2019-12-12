package com.mbooks.microservicebooks.controller;

import com.mbooks.microservicebooks.dao.BookService;
import com.mbooks.microservicebooks.dao.BorrowingDao;
import com.mbooks.microservicebooks.dao.BorrowingTypeDao;
import com.mbooks.microservicebooks.exceptions.InvalidRequestException;
import com.mbooks.microservicebooks.exceptions.NotFoundException;
import com.mbooks.microservicebooks.model.Borrowing;
import com.mbooks.microservicebooks.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <h2>Controller for the model Borrowing</h2>
 */
@RestController
public class BorrowingController {
    @Autowired
    private BorrowingDao borrowingDao;
    @Autowired
    private BorrowingTypeDao borrowingTypeDao;
    @Autowired
    private BookService bookService;

    /**
     * <p>Lists all borrowings</p>
     * @return list
     */
    @GetMapping(value="/Prets")
    public List<Borrowing> listBorrowings() {
        List<Borrowing> borrowings = borrowingDao.findAll();
        if(borrowings.isEmpty()) throw new NotFoundException("Aucun prêt n'est enregistré");
        return borrowings;
    }
    /**
     * <h2>Not needed for user application => for employees application</h2>
     * <p>adds a new borrowing in db</p>
     * @param borrowing
     * @return responseEntity
     */
    //TODO gérer la priorité sur l'emprunt d'un livre réserver pendant 48H?
    @PostMapping(value = "/Prets/add-borrowing")
    public ResponseEntity<Void> addBorrowing(@Valid @RequestBody Borrowing borrowing) {
        ZoneId zone = ZoneId.of("Europe/Paris");
        LocalDate today = LocalDate.now(zone);
        LocalDate oneMonthLater = today.plusMonths( 1 );

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        borrowing.setBorrowed(today.format(format));
        borrowing.setLimitDate(oneMonthLater.format(format));

        Borrowing borrowingAdded =  borrowingDao.save(borrowing);
        if (borrowingAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(borrowingAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    /**
     * <h2>Not needed for user application => for employees application</h2>
     * <p>sets a borrowing in db to returned</p>
     * @param id
     * @return borrowing
     */
    @PostMapping(value = "/Prets/{id}/return")
    public Borrowing returnBorrowing(@PathVariable Integer id) {
        Optional<Borrowing> borrow = borrowingDao.findById(id);
        if(!borrow.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        Borrowing borrowing = borrowingDao.findBorrowingById(id);
        ZoneId zone = ZoneId.of("Europe/Paris");
        LocalDate today = LocalDate.now(zone);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        borrowing.setReturned(today.format(format));
        borrowing.setBorrowingType(borrowingTypeDao.findBorrowingTypeById(2));
        Borrowing borrowingAdded =  borrowingDao.save(borrowing);
        if (borrowingAdded == null) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        bookService.checkForWaitingList(borrowing.getBook());
        return borrowing;
    }
    /**
     * <p>show details of a particular borrowing by its id</p>
     * @param id
     * @return the category
     */
    @GetMapping(value = "/Prets/{id}")
    public Optional<Borrowing> showBorrowing(@PathVariable Integer id) {
        Optional<Borrowing> borrow = borrowingDao.findById(id);
        if(!borrow.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        return borrow;
    }

    /**
     * <p>show details of a particular borrowing by its id</p>
     * @param id
     * @return the category
     */
    @GetMapping(value = "/Prets/{id}/renew")
    public Borrowing renewBorrowing(@PathVariable Integer id) {
        Optional<Borrowing> borrowSearch = borrowingDao.findById(id);
        if(!borrowSearch.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        Borrowing borrow = borrowingDao.findBorrowingById(id);
        if(borrow.getRenewed() == true){
            throw new InvalidRequestException("Le prêt avec l'id "+borrow.getId()+" a déjà été renouvelé une fois");
        }
        borrow.setRenewed(true);
        String getdate = borrow.getLimitDate();
        try{
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(getdate);
            LocalDate limitDate = DateUtils.convertToLocalDateViaInstant(date);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            borrow.setLimitDate(limitDate.plusMonths(1).format(formatter));
            borrow.setBorrowingType(borrowingTypeDao.findBorrowingTypeById(3));
        }catch (Exception e){
            e.printStackTrace();
        }
        Borrowing borrowingAdded =  borrowingDao.save(borrow);
        if (borrowingAdded == null) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        return borrowingAdded;
    }
    /**
     * <p>show details of a particular borrowing by its id</p>
     * @param id
     * @return the category
     */
    @GetMapping(value = "/Prets/Utilisateur/{id}")
    public List<Borrowing> showUserBorrowing(@PathVariable Integer id) {
        List<Borrowing> borrowed = borrowingDao.findBorrowingByIdUser(id);
        if(!borrowed.isEmpty()) {
            throw new NotFoundException("Aucun prêt pur l'id " + id + ".");
        }
        return borrowed;
    }
}
