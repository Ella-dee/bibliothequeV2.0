package com.mclientui.microserviceclientui.web.controller;

import com.mclientui.microserviceclientui.beans.BorrowingBean;
import com.mclientui.microserviceclientui.beans.UserBean;
import com.mclientui.microserviceclientui.exceptions.BadLoginPasswordException;
import com.mclientui.microserviceclientui.proxies.MicroserviceBooksProxy;
import com.mclientui.microserviceclientui.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Controller linking with microservice-users</h2>
 */
@Controller
public class ClientUsersController {

    @Autowired
    private MicroserviceUsersProxy usersProxy;
    @Autowired
    private MicroserviceBooksProxy booksProxy;
    /*
     **************************************
     * User login
     * ************************************
     */

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


    @RequestMapping("/Utilisateurs/log-user")
    public String logUser(@ModelAttribute("user") UserBean userBean, ModelMap model, HttpServletRequest request) {
        String toBeReturned;
        HttpSession session = request.getSession();
        try{
            UserBean userToConnect = usersProxy.logUser(userBean.getUserName(), userBean.getPassword());
            String redirectString = "/Utilisateurs/MonProfil/"+userToConnect.getId();
            session.setAttribute("loggedInUserEmail", userToConnect.getEmail());
            session.setAttribute("loggedInUserId", userToConnect.getId());
            session.setAttribute("loggedInUserRole", userToConnect.getUserRole().getRoleName());

            toBeReturned = "redirect:"+redirectString;
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof BadLoginPasswordException){
                model.addAttribute("errorMessage", "Login ou Mot de Passe incorrect");
            }
            toBeReturned = "login";
        }
        return toBeReturned;
    }

    /**
     * shows details of particular user with its id
     * @param userId
     * @param model
     * @return user-details.html
     */
    @RequestMapping("/Utilisateurs/MonProfil/{userId}")
    public String userDetails(@PathVariable Integer userId, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserBean user = usersProxy.showUser(userId);

        if(!session.getAttribute("loggedInUserId").equals(userId)){
            System.out.println("User trying to access profile is not the owner of the profile");
            System.out.println("User is: [id:"
                    +session.getAttribute("loggedInUserId")+ ", email:"
                    +session.getAttribute("loggedInUserEmail")+", role:"
                    +session.getAttribute("loggedInUserRole")+"]");
            return "redirect:/Accueil";
        }

        List<BorrowingBean> borrowingBeanList=booksProxy.listBorrowings();
        List<BorrowingBean> userBorrowings = new ArrayList<>();
        for(BorrowingBean borrowingBean:borrowingBeanList){
            if(borrowingBean.getIdUser() == userId){
                userBorrowings.add(borrowingBean);
            }
        }
        System.out.println("USERBORROWINGS");
        System.out.println(userBorrowings.toString());
      /*  List<BorrowingBean> borrowings = booksProxy.showUserBorrowing(userId);
        System.out.println("BORROWINGS");
        System.out.println(borrowings);*/


        model.addAttribute("user", user);
        model.addAttribute("borrowings", userBorrowings);
        model.addAttribute("session", session);
        return "user-details";
    }
    /*
     **************************************
     * User logout
     * ************************************
     */
    /**
     * Process called after the logout button is clicked in navbar
     * @param session session
     * @return homepage
     */
    @RequestMapping(value = "Utilisateurs/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/Accueil";
    }


}