package com.mclientui.microserviceclientui.web.controller;

import com.mclientui.microserviceclientui.beans.BookBean;
import com.mclientui.microserviceclientui.beans.BorrowingBean;
import com.mclientui.microserviceclientui.beans.UserBean;
import com.mclientui.microserviceclientui.beans.WaitingListBean;
import com.mclientui.microserviceclientui.exceptions.BadLoginPasswordException;
import com.mclientui.microserviceclientui.proxies.MicroserviceBooksProxy;
import com.mclientui.microserviceclientui.proxies.MicroserviceMailingProxy;
import com.mclientui.microserviceclientui.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
            toBeReturned = setSessionAttributes(userToConnect, session);
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
            rejectIfSessionNotProfile(session, user);
        }

        List<BorrowingBean> borrowingBeanList=booksProxy.listBorrowings();
        List<BorrowingBean> userBorrowings = new ArrayList<>();
        for(BorrowingBean b:borrowingBeanList){
            if(b.getIdUser() == userId){
                userBorrowings.add(b);
            }
        }

        List<WaitingListBean> userWaitingList= booksProxy.showUserWaitingList(userId);
        List<BookBean> booksInWaiting = new ArrayList<>();
        if(!userWaitingList.isEmpty()) {
            for (WaitingListBean w : userWaitingList) {
                BookBean b = booksProxy.showBook(w.getBook().getId());
                b.setUserPosOnWaitingList(w.getUserPos());
                b.setWaitingListId(w.getId());
                if (b.getClosestReturnDate()==null){
                    b.setClosestReturnDate("Disponible, vous avez 48h pour emprunter ce livre en priorité");
                }
                booksInWaiting.add(b);

            }
        }
        model.addAttribute("user", user);
        model.addAttribute("borrowings", userBorrowings);
        model.addAttribute("awaitedBooks", booksInWaiting);
        model.addAttribute("session", session);
        return "user-details";
    }

    @RequestMapping(value = "/Reservations/delete/{id}")
    public String cancelWaitingList(@PathVariable Integer id, HttpServletRequest request){
        HttpSession session = request.getSession();
        WaitingListBean waitingListBean = booksProxy.showWaitingList(id);
        UserBean user = usersProxy.showUser(waitingListBean.getUserId());
        //TODO uncomment after postman tests purposes
        /*if(!session.getAttribute("loggedInUserId").equals(user.getId())){
            System.out.println("Profile user id is: "+user.getId());
            rejectIfSessionNotProfile(session, user);
        }*/
        System.out.println(id);
        booksProxy.cancelWaitingList(id);
        return  "redirect:/Utilisateurs/MonProfil/"+user.getId();
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
        UserBean userToFind = usersProxy.findUserForPassword(userBean.getEmail());
        String appUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        try{
                mailingProxy.sendLinkForPassword(userToFind.getEmail(), userToFind.getResetToken(), appUrl);
                theModel.addAttribute("successMessage", "Un email a été envoyé à l'adresse indiquée");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("EMAIL INPUT = "+userBean.getEmail());
            theModel.addAttribute("errorMessage", "email inconnu");
        }
        return "password-forgot";
    }

    /**
     *<p>displays form to reset password</p>
     * @param model model form
     * @return page form
     */
  @RequestMapping(value = "/Utilisateurs/MotDePasseReset", method = RequestMethod.GET)
    public String resetPasswordForm(Model model, @RequestParam("token") String token) {
      String redirectPage = "";
      try {
          UserBean userBean = usersProxy.findUserByToken(token);
          model.addAttribute("user", userBean);
          model.addAttribute("resetToken", token);
          redirectPage = "password-reset";
      } catch (Exception e) {
          e.printStackTrace();
          redirectPage = "redirect:/Utilisateurs/connexion";
      }
      return redirectPage;
  }

    /**
     * <p>process called after password is reset</p>
     * @param userBean user
     * @param theModel modelpage
     * @return modelandview
     */
    @RequestMapping (value = "/Utilisateurs/MotDePasseReset", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute("user") UserBean userBean, ModelMap theModel){
        int success = 0;
        UserBean userToresetPassword = usersProxy.findUserByTokenAndSetsNewPassword(userBean.getResetToken(), userBean.getPassword());
        success = 1;
        theModel.addAttribute("success", success);
        return "password-reset";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex){
        return new ModelAndView("redirect:/Utilisateurs/connexion");
    }

    /**
     * <p>Sets session attributes for a user</p>
     * @param user
     * @param session
     * @return url
     */
    public String setSessionAttributes(UserBean user, HttpSession session){
        String redirectString = "/Utilisateurs/MonProfil/"+user.getId();
        session.setAttribute("loggedInUserEmail", user.getEmail());
        session.setAttribute("loggedInUserId", user.getId());
        session.setAttribute("loggedInUserRole", user.getUserRole().getRoleName());
        return "redirect:"+redirectString;
    }

    /**
     * <p>Send back to homepage if session user Id != profile id</p>
     * @param session
     * @param user
     * @return homepage
     */
    public String rejectIfSessionNotProfile(HttpSession session, UserBean user){
            System.out.println("User trying to access profile is not the owner of the profile");
            System.out.println("User is: [id:"
                    +session.getAttribute("loggedInUserId")+ ", email:"
                    +session.getAttribute("loggedInUserEmail")+", role:"
                    +session.getAttribute("loggedInUserRole")+"]");
            return "redirect:/Accueil";

    }

}