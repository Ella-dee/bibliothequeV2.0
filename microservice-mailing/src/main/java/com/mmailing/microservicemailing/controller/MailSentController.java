package com.mmailing.microservicemailing.controller;

import com.mmailing.microservicemailing.beans.UserBean;
import com.mmailing.microservicemailing.dao.MailSentDao;
import com.mmailing.microservicemailing.dao.MailSentForWaitingListDao;
import com.mmailing.microservicemailing.exceptions.NotFoundException;
import com.mmailing.microservicemailing.mailing.MailService;
import com.mmailing.microservicemailing.model.MailSent;
import com.mmailing.microservicemailing.model.MailSentForWaitingList;
import com.mmailing.microservicemailing.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.mmailing.microservicemailing.utils.DateUtils.localDateToString;
import static com.mmailing.microservicemailing.utils.DateUtils.theDateToday;

/**
 * <h2>Controller for the MailSent model</h2>
 */
@Controller
public class MailSentController {
    @Autowired
    private MailSentDao mailSentDao;
    @Autowired
    private static MicroserviceUsersProxy usersProxy;
    @Autowired
    private static MailService mailService;
    @Autowired
    private MailSentForWaitingListDao mailSentForWaitingListDao;

    /**
     * <p>Lists all reminder mails that were sent</p>
     * @return list
     */
    @GetMapping(value="/Mails")
    public List<MailSent> listAuthors() {
        List<MailSent> mailSentList = mailSentDao.findAll();
        if(mailSentList.isEmpty()) throw new NotFoundException("Aucun mail n'est enregistré");
        return mailSentList;
    }

    /**
     * <p>adds a new mail in db</p>
     * @param mailSent
     * @return responseEntity
     */
    @PostMapping(value = "/Mails")
    public ResponseEntity<Void> addMail(@Valid @RequestBody MailSent mailSent) {
        MailSent mailAdded =  mailSentDao.save(mailSent);
        if (mailAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(mailAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    /**
     * <p>shows details of a particular mail by its id</p>
     * @param id
     * @return the author
     */
    @GetMapping(value = "/Mails/{id}")
    public Optional<MailSent> showAuthor(@PathVariable Integer id) {
        Optional<MailSent> mailSent = mailSentDao.findById(id);
        if(!mailSent.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        return mailSent;
    }

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

    @PostMapping(value = "/Utilisateurs/notification_retour")
    public void  sendNotifWhenAwaitedBookIsReturned(@RequestParam Integer userId, @RequestParam Integer bookId){
        UserBean user = usersProxy.showUser(userId);
        String subject = "Un Livre que vous attendez est disponible";
        String message = "Vous avez la priorité sur ce livre pendant 48H, après ce délai vous serez retiré de la liste d'attente\n";
        try{
            mailService.sendSimpleMessage(user.getEmail(), subject, message);
        }catch (Exception e){
            e.printStackTrace();
        }
        MailSentForWaitingList mailSentForWaitingList = new MailSentForWaitingList();
        mailSentForWaitingList.setSentDate(localDateToString(theDateToday()));
        mailSentForWaitingList.setIdBook(bookId);
        mailSentForWaitingList.setIdUser(userId);
        mailSentForWaitingListDao.save(mailSentForWaitingList);
    }
}
