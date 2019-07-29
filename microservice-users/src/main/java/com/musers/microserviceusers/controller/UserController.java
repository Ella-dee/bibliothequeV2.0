package com.musers.microserviceusers.controller;

import com.musers.microserviceusers.dao.UserDao;
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


    @PostMapping("/Utilisateurs/login")
    public String login(@ModelAttribute("user") User theUser, BindingResult theBindingResult, HttpServletRequest request) {
        userLoginValidator.validate(theUser, theBindingResult);
        HttpSession session = request.getSession();

        if (theBindingResult.hasErrors()) {
            return "login";
        }
        else{
            User userToLogIn = userDao.findByUserName(theUser.getUserName());
            System.out.println("USERTOLOGIN"+userToLogIn);
            session.setAttribute("loggedInUserEmail", userToLogIn.getEmail());
            session.setAttribute("loggedInUserId", userToLogIn.getId());
            session.setAttribute("loggedInUserRole", userToLogIn.getUserRole().getId());
            String redirectString = "/Utilisateurs/MonProfil/"+userToLogIn.getId();
            return "redirect:"+redirectString;
        }
    }
   
}
