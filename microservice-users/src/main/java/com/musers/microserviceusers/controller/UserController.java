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
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    /**
     * <p>method to log users</p>
     * @param userName from form
     * @param password from form
     * @return response entity of user
     */
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

    /**
     * <p>finds a user by mail to reset a password (sets a token in db)</p>
     * @param email from form
     * @return a user
     */
    @PostMapping(value = "/Utilisateurs/forgot-password")
    public User findUserForPassword(@RequestParam String email) {
        try{
            User userToFind = userDao.findByEmail(email);
            Optional<User> user = userDao.findById(userToFind.getId());
            if(!user.isPresent()) {
                throw new NotFoundException("L'utilisateur avec l'id " + userToFind.getId() + " est INTROUVABLE.");
            }
            userToFind.setResetToken(UUID.randomUUID().toString());
            ZoneId zone = ZoneId.of("Europe/Paris");
            LocalDate today = LocalDate.now(zone);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            //set token valid for 1 day
            userToFind.setTokenDate(today.format(format));
            userDao.save(userToFind);
            return userToFind;
        }catch (Exception e){
            e.printStackTrace();
            throw new NotFoundException("L'utilisateur avec l'email " + email + " est INTROUVABLE.");
        }
    }

    /**
     * <p>finds a user by token set to reset a password</p>
     * @param token
     * @return a user
     */
    @GetMapping (value = "/Utilisateurs/MotDePasseReset")
    public ResponseEntity<User> findUserByToken(@RequestParam String token) {
            Optional<User> user = userDao.findByResetToken(token);
            if(!user.isPresent()) {
                throw new NotFoundException("L'utilisateur avec le token " + token+ " est INTROUVABLE.");
            }
            User userToFind = user.get();
        return new ResponseEntity<User>(userToFind, HttpStatus.OK);
    }

    /**
     * <p>resets a user's password</p>
     * @param token reset token
     * @param password new password
     * @return user
     */
    @PostMapping(value = "/Utilisateurs/MotDePasseReset")
    public Optional<User> findUserByTokenAndSetsNewPassword(@RequestParam String token, @RequestParam String password) {
        Optional<User> user = userDao.findByResetToken(token);
        if(!user.isPresent()) {
            throw new NotFoundException("L'utilisateur avec le token " + token+ " est INTROUVABLE.");
        }
        else {
            User resetUser = user.get();
            resetUser.setPassword(Encryption.encrypt(password));
            resetUser.setResetToken(null);
            resetUser.setTokenDate(null);
            userDao.save(resetUser);
        }
        return user;
    }


   
}
