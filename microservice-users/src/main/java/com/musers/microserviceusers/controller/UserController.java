package com.musers.microserviceusers.controller;

import com.musers.microserviceusers.dao.UserDao;
import com.musers.microserviceusers.exceptions.NotFoundException;
import com.musers.microserviceusers.model.User;
import com.musers.microserviceusers.utils.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * <h2>Controller for Model User</h2>
 */
@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    /**
     * <p>Lists all users</p>
     * @return a list
     */
    @GetMapping(value="/Utilisateurs")
    public List<User> listUsers() {
        return userDao.findAll();
    }

    /**
     * <p>Adds a new user to db, encrypts password before save</p>
     * @param user
     * @return responseEntity
     */
    @PostMapping(value = "/Utilisateurs/add-user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        user.setPassword(Encryption.encrypt(user.getPassword()));
        User userAdded =  userDao.save(user);
        if (userAdded == null) {throw new CannotAddException("Impossible d'ajouter l'utilisateur");}
        return new ResponseEntity<User>(userAdded, HttpStatus.CREATED);
    }
    /*
    @PostMapping (value = "/commandes")
    public ResponseEntity<Commande> ajouterCommande(@RequestBody Commande commande){
        Commande nouvelleCommande = commandesDao.save(commande);
        if(nouvelleCommande == null) throw new ImpossibleAjouterCommandeException("Impossible d'ajouter cette commande");
        return new ResponseEntity<Commande>(commande, HttpStatus.CREATED);
    }
     */

    /**
     * <p>shows details of a particular user by its id</p>
     * @param id
     * @return the user
     */
    @GetMapping(value = "/Utilisateurs/{id}")
    public Optional<User> showUser(@PathVariable Integer id) {
        Optional<User> user = userDao.findById(id);
        if(!user.isPresent()) {
            throw new NotFoundException("L'utilisateur avec l'id " + id + " est INTROUVABLE.");
        }
        return user;
    }
   
}
