package com.mmailing.microservicemailing.controller;

import com.mmailing.microservicemailing.beans.UserBean;
import com.mmailing.microservicemailing.dao.MailSentForWaitingListService;
import com.mmailing.microservicemailing.mailing.MailService;
import com.mmailing.microservicemailing.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <h2>Controller for the Mails</h2>
 */
@Controller
public class MailSentController {
    @Autowired
    private  MicroserviceUsersProxy usersProxy;
    @Autowired
    private  MailService mailService;
    @Autowired
    private  MailSentForWaitingListService mailSentForWaitingListService;


    @PostMapping(value = "/Utilisateurs/forgot-password")
    public void   sendLinkForPassword(@RequestParam String email, @RequestParam String token, @RequestParam String appUrl){
        String subject = "Réinitialisation Mot de Passe";
        String message = "Pour réinitialiser votre mdp, cliquer sur le lien suivant:\n" + appUrl+"/Utilisateurs/MotDePasseReset?token="+token;
        try{
            mailService.sendSimpleMessage(email, subject, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/Utilisateurs/notification-retour")
    public void  sendNotifWhenAwaitedBookIsReturned(@RequestParam Integer userId, @RequestParam Integer bookId){
        UserBean user = usersProxy.showUser(userId);
        mailSentForWaitingListService.sendNotif(userId, bookId, user.getEmail(), user.getFirstName());
    }
}
