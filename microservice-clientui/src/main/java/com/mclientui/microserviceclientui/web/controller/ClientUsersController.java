package com.mclientui.microserviceclientui.web.controller;

import com.mclientui.microserviceclientui.beans.BorrowingBean;
import com.mclientui.microserviceclientui.beans.UserBean;
import com.mclientui.microserviceclientui.exceptions.BadLoginPasswordException;
import com.mclientui.microserviceclientui.proxies.MicroserviceBooksProxy;
import com.mclientui.microserviceclientui.proxies.MicroserviceMailingProxy;
import com.mclientui.microserviceclientui.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <h2>Controller linking with microservice-users</h2>
 */
@Controller
public class ClientUsersController {

    @Autowired
    private MicroserviceUsersProxy usersProxy;
    @Autowired
    private MicroserviceBooksProxy booksProxy;
    @Autowired
    private MicroserviceMailingProxy mailingProxy;
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
            System.out.println("FIELDS: "+userBean.getUserName()+" "+userBean.getPassword());
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
    @RequestMapping(value = "/Utilisateurs/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/Accueil";
    }

  /*
     **************************************
     * User reset password
     * ************************************
     */

    /**
     *<p>displays form to send an email to retrieve a link to reset password</p>
     * @param model user
     * @return page with form
     */
    @GetMapping(value = "/Utilisateurs/MotDePasseOublie")
    public String forgotPassword(Model model) {
        UserBean userBean = new UserBean();
        model.addAttribute("user", userBean);
        return "password-forgot";
    }

    /**
     *<p>process called when form to send reset link is validated</p>
     * @param userBean user
     * @param theModel form model
     * @param request servlet request
     * @return page form
     */
    @RequestMapping("/Utilisateurs/forgot-password")
    public String findUserSendLinkForPassword(@ModelAttribute("user") UserBean userBean, ModelMap theModel, HttpServletRequest request) {
        UserBean userToFind = new UserBean();
        String appUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        try{
            userToFind = usersProxy.findUserForPassword(userBean.getEmail());
            theModel.addAttribute("successMessage", "Un email a été envoyé à l'adresse indiquée");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("EMAIL INPUT = "+userBean.getEmail());
            theModel.addAttribute("errorMessage", "email inconnu");
        }
        //TODO le mail est bien envoyé mais renvoi le catch: FeignException: status 500 reading MicroserviceMailingProxy#sendLinkForPassword(String,String,String)
        try{
            mailingProxy.sendLinkForPassword(userToFind.getEmail(), userToFind.getResetToken(), appUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "password-forgot";
    }

    /**
     *<p>displays form to reset password</p>
     * @param model model form
     * @return page form
     */
    @RequestMapping(value = "/Utilisateurs/MotDePasseResetForm")
    public String resetPasswordForm(Model model, @RequestParam("token") String token) {
        UserBean userBean = new UserBean();
        try{
            userBean = usersProxy.findUserByToken(token);
            System.out.println(userBean.getEmail());
            model.addAttribute("user", userBean);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(userBean.getEmail());
        }
        return "password-reset";
    }

    /**
     * <p>process called after password is reset</p>
     * @param userBean user
     * @param token reset token
     * @param theModel modelpage
     * @return modelandview
     */
    @RequestMapping (value = "/Utilisateurs/MotDePasseReset")
    public String resetPassword(@ModelAttribute("user") UserBean userBean, @RequestParam("token") String token, ModelMap theModel){
        System.out.println("INSIDE CLIENT METHOD FOR RESET");
        int success = 0;
        try{
            System.out.println("INSIDE USER PROXY, INSIDE TRY");
            UserBean userToresetPassword = usersProxy.findUserByTokenAndSetsNewPassword(token, userBean.getPassword());
            success = 1;
            theModel.addAttribute("success", success);
        }catch (Exception e){
            System.out.println("INSIDE USER PROXY, INSIDE CATCH");
            e.printStackTrace();
        }
        System.out.println("INSIDE USER PROXY, OUT OF LOOP");
        return "password-reset";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex){
        return new ModelAndView("redirect:/Utilisateurs/connexion");
    }

}