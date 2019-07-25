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

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping(value="/Utilisateurs")
    public List<User> listUsers() {
        return userDao.findAll();
    }

    @PostMapping(value = "/Utilisateurs")
    public ResponseEntity<Void> addUser(@Valid @RequestBody User user) {
        user.setPassword(Encryption.encrypt(user.getPassword()));
        User userAdded =  userDao.save(user);
        if (userAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    
    @GetMapping(value = "/Utilisateurs/{id}")
    public Optional<User> showAuthor(@PathVariable Integer id) {
        Optional<User> user = userDao.findById(id);
        if(!user.isPresent()) {
            throw new NotFoundException("L'utilisateur avec l'id " + id + " est INTROUVABLE.");
        }
        return user;
    }
   
}
