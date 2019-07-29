package com.mclientui.microserviceclientui.web.controller;

import com.mclientui.microserviceclientui.beans.UserBean;
import com.mclientui.microserviceclientui.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    /**
     * <p>Page that displays a form to login a user</p>
     * @param model attribute passed to jsp page
     * @return login page
     */
    @GetMapping("/Utilisateurs/connexion")
    public String loginPage(Model model) {
        UserBean userBean = new UserBean();
        model.addAttribute("user", userBean);
        return "login";
    }

    @RequestMapping("/Utilisateurs/login")
    public String login(@ModelAttribute("user") UserBean userBean) {
        UserBean userToConnect = usersProxy.login(userBean);

        return "login";
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
    @RequestMapping("/Utilisateurs/MonProfil/{userId}")
    public String userDetails(@PathVariable Integer userId, Model model){
        UserBean user = usersProxy.showUser(userId);
        model.addAttribute("user", user);
        return "user-details";
    }



}