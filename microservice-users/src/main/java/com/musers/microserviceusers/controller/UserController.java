package com.musers.microserviceusers.controller;

import com.musers.microserviceusers.dao.UserDao;
import com.musers.microserviceusers.exceptions.BadLoginPasswordException;
import com.musers.microserviceusers.exceptions.CannotAddException;
import com.musers.microserviceusers.exceptions.NotFoundException;
import com.musers.microserviceusers.model.User;
import com.musers.microserviceusers.utils.Encryption;
import com.musers.microserviceusers.utils.validators.UserLoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * <h2>Controller for Model User</h2>
 */
@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLoginValidator userLoginValidator;
    /**
     * <p>Lists all users</p>
     * @return a list
     */
    @GetMapping(value="/Utilisateurs")
    public List<User> listUsers() {
        return userDao.findAll();
    }

    /**
     * <h2>Not needed for user application => for employees application</h2>
     * <p>Adds a new user to db, encrypts password before save</p>
     * @param user
     * @return responseEntity
     */
    @PostMapping(value = "/Utilisateurs/add-user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        user.setPassword(Encryption.encrypt(user.getPassword()));
        User userAdded =  userDao.save(user);
        if (userAdded == null) {throw new CannotAddException("User03");}
        return new ResponseEntity<User>(userAdded, HttpStatus.CREATED);
    }


    /**
     * <p>shows details of a particular user by its id</p>
     * @param id
     * @return the user
     */
    @GetMapping(value = "/Utilisateurs/MonProfil/{id}")
    public Optional<User> showUser(@PathVariable Integer id) {
        Optional<User> user = userDao.findById(id);
        if(!user.isPresent()) {
            throw new NotFoundException("L'utilisateur avec l'id " + id + " est INTROUVABLE.");
        }
        return user;
    }

    @PostMapping(value = "/Utilisateurs/log-user")
    public ResponseEntity<User> logUser(@RequestParam String userName, @RequestParam String password) {
        User userLogged =  userDao.findByUserName(userName);
        if (userLogged == null) {throw new BadLoginPasswordException("User01");}

        String loginPassword = Encryption.encrypt(password);
        if (!loginPassword.equals(userLogged.getPassword())) {
            throw new BadLoginPasswordException("User02");
        }
        return new ResponseEntity<User>(userLogged, HttpStatus.OK);
    }


   
}
