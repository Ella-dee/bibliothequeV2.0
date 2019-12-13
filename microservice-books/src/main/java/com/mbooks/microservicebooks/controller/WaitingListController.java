package com.mbooks.microservicebooks.controller;

import com.mbooks.microservicebooks.dao.BookDao;
import com.mbooks.microservicebooks.dao.BorrowingDao;
import com.mbooks.microservicebooks.dao.WaitingListDao;
import com.mbooks.microservicebooks.exceptions.InvalidRequestException;
import com.mbooks.microservicebooks.exceptions.NotFoundException;
import com.mbooks.microservicebooks.model.Borrowing;
import com.mbooks.microservicebooks.model.WaitingList;
import com.mbooks.microservicebooks.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * <h2>Controller for the model waitingList</h2>
 */
@RestController
public class WaitingListController {
    @Autowired
    private WaitingListDao waitingListDao;

    @Autowired
    private BorrowingDao borrowingDao;


    /**
     * <p>Lists all waitingLists</p>
     * @return list
     */
    @GetMapping(value="/Reservations")
    public List<WaitingList> listwaitingLists() {
        List<WaitingList> waitingLists = waitingListDao.findAll();
        if(waitingLists.isEmpty()) throw new NotFoundException("Aucune liste d'attente n'est enregistrée");
        return waitingLists;
    }
    /**
     * <p>adds a new waitingList for book/user in db</p>
     * @param waitingList
     * @return responseEntity
     */
    @PostMapping(value = "/Reservations/add-userToWaitingList")
    public ResponseEntity<Void> addUserToWaitingList(@Valid @RequestBody WaitingList waitingList) {
        //waitingList contains from clientUI: idUser, book
        //create userPos if possible:
        //1- look for occurrences with the same book
        Integer bookAwaitedId = waitingList.getBook().getId();
        Integer maxWaitingSize = waitingList.getBook().getNbr()*2;
        List<WaitingList> waitingForThisBook = waitingListDao.findWaitingListByBook_Id(bookAwaitedId);
        //2- check if user is not already in the waitingList
        for (WaitingList list: waitingForThisBook){
            if (list.getIdUser() == waitingList.getIdUser()){
                throw new InvalidRequestException("Utilisateur déjà sur liste d'attente");
            }
        }
        //2bis - check if user has borrowing with this book already
        List<Borrowing> borrowingsOfThisBook = borrowingDao.findBorrowingByBook_Id(bookAwaitedId);
        if (borrowingsOfThisBook.size()>0){
            for (Borrowing b:borrowingsOfThisBook){
                if (b.getIdUser().equals(waitingList.getIdUser())){
                    throw new InvalidRequestException("Utilisateur a déjà un prêt en cours pour ce livre");
                }
            }
        }
        //3- check if waitingList has enough room for another User
        if (waitingForThisBook.size() >= maxWaitingSize){
            throw new InvalidRequestException("Liste d'attente déjà trop longue");
        }
        waitingList.setUserPos(waitingForThisBook.size()+1);
        //4- save waitingList to DB
        WaitingList waitingListAdded =  waitingListDao.save(waitingList);
        if (waitingListAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(waitingListAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * <p>Deletes a line for couple user/book if user cancels reservation/p>
     * @param id
     * @return waitingList
     */
    @PostMapping(value = "/Reservations/delete/{id}")
    public void cancelwaitingList(@PathVariable Integer id) {
        Optional<WaitingList> waitingList = waitingListDao.findById(id);
        if(!waitingList.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        WaitingList waitingListToDelete = waitingListDao.getOne(id);
        //TODO etudier si on garde userPos, où si on recalcul par les id des waitingList et Collections.sort()
        //change userPos if possible:
        Integer canceledPos = waitingListToDelete.getUserPos();
        //1- find the book
        Integer bookAwaitedId = waitingListToDelete.getBook().getId();
        //2- delete the one to delete
        waitingListDao.delete(waitingListToDelete);
        //3- find other waitings for this book
        List<WaitingList> waitingForThisBook = waitingListDao.findWaitingListByBook_Id(bookAwaitedId);
        //4- if others are waiting reset user positions
        if (waitingForThisBook.size()>0){
            for (WaitingList list: waitingForThisBook){
                //if user is not first in line and canceledPos frees a space: move up
                if (list.getUserPos() != 1 && canceledPos < list.getUserPos() ){
                    list.setUserPos(list.getUserPos()-1);
                    waitingListDao.save(list);
                }
            }
    }

    /**
     * <p>show details of a particular waitingList by its id</p>
     * @param id
     * @return the category
     */
    @GetMapping(value = "/Reservations/{id}")
    public Optional<WaitingList> showwaitingList(@PathVariable Integer id) {
        Optional<WaitingList> list = waitingListDao.findById(id);
        if(!list.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        return list;
    }

    /**
     * <p>show details of a particular waitingList by its id for a User</p>
     * @param id
     * @return the category
     */
    @GetMapping(value = "/Reservations/Utilisateur/{id}")
    public List<WaitingList> showUserwaitingList(@PathVariable Integer id) {
        List<WaitingList> list = waitingListDao.findWaitingListByIdUser(id);
        if(!list.isEmpty()) {
            throw new NotFoundException("Aucun prêt pur l'id " + id + ".");
        }
        return list;
    }
}
