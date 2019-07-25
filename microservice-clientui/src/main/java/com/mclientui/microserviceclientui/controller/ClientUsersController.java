package com.mclientui.microserviceclientui.controller;

import com.mclientui.microserviceclientui.beans.AuthorBean;
import com.mclientui.microserviceclientui.beans.BookBean;
import com.mclientui.microserviceclientui.beans.UserBean;
import com.mclientui.microserviceclientui.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <h2>Controller linking with microservice-users</h2>
 */
@Controller
public class ClientUsersController {

    @Autowired
    private MicroserviceUsersProxy usersProxy;

    /**
     * <p>Lists all users</p>
     * @param model
     * @return users.html
     */
    @RequestMapping("/Utilisateurs")
    public String listUsers(Model model){
        List<UserBean> users =  usersProxy.listUsers();
        model.addAttribute("users", users);
        return "users";
    }

 /*   @RequestMapping("/Utilisateurs/add_user")
    public ResponseEntity<UserBean> addUser(@Valid @RequestBody UserBean userBean){
        UserBean newUser = usersProxy.addUser(userBean);
        //TODO comment récupérer id???
        return "user-details/"+usersProxy.showUser(newUser);
    }*/

    /**
     * shows details of particular user with its id
     * @param userId
     * @param model
     * @return user-details.html
     */
    @RequestMapping("/Utilisateurs/{userId}")
    public String userDetails(@PathVariable Integer userId, Model model){
        UserBean user = usersProxy.showUser(userId);
        model.addAttribute("user", user);
        return "user-details";
    }



}