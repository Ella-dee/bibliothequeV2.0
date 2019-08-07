package com.mmailing.microservicemailing.batch;

import com.mmailing.microservicemailing.beans.BookBean;
import com.mmailing.microservicemailing.beans.BorrowingBean;
import com.mmailing.microservicemailing.beans.UserBean;
import com.mmailing.microservicemailing.dao.MailSentDao;
import com.mmailing.microservicemailing.dao.MailTypeDao;
import com.mmailing.microservicemailing.mailing.MailService;
import com.mmailing.microservicemailing.model.MailSent;
import com.mmailing.microservicemailing.proxies.MicroserviceBooksProxy;
import com.mmailing.microservicemailing.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <h1>Class that checks late status for borrowings and send mails accordingly</h1>
 */
@Component
public class TaskOne {
    @Autowired
    private MicroserviceBooksProxy booksProxy;
    @Autowired
    private MicroserviceUsersProxy usersProxy;
    @Autowired
    private MailService mailService;
    @Autowired
    private MailSentDao mailSentDao;
    @Autowired
    private MailTypeDao mailTypeDao;
    /**
     * <p>method called when application is ran</p>
     *  <p>checks if borrowing status is "late" and renewable, and if so sends mails</p>
     */
    public void checkAndSendRenewableBorrowings(){
        List<BorrowingBean> borrowings = booksProxy.listBorrowings();
        for(BorrowingBean borrowing:borrowings) {
            //get info and calls proper mailMessage for borrowings that can be renewed
            if (borrowing.getBorrowingType().getId() == 4 && borrowing.getRenewed() == false) {
                UserBean user = usersProxy.showUser(borrowing.getIdUser());
                BookBean book = booksProxy.showBook(borrowing.getBook().getId());
                String subject = "Retour du livre " + book.getTitle();
                String text = "Bonjour "+user.getFirstName()+" "+user.getLastName()+
                        "\n\nVous avez emprunté le livre '"+book.getTitle()+"' le "+ borrowing.getBorrowed()+"."+
                        "\nLa date limite de retour était le "+borrowing.getLimitDate()+"."+
                        "\nVous avez la possibilité de renouveler une fois votre emprunt, pour une durée de 4semaines."+
                        "\nMerci de bien vouloir vous rendre à la bibliothèque, ou répondre à ce mail, afin d'effectuer "+
                        "le retour du livre ou la prolongation de l'emprunt."+
                        "\n\nA bientôt et bonne lecture, "+
                        "\n\nLa bilibothèque de la ville";


                try{
                    mailService.sendSimpleMessage(user.getEmail(), subject, text);
                    MailSent email = prepareModelMailSent(theDateToday(), user.getEmail(), user.getId(), book.getId(), book.getTitle());
                    email.setMailType(mailTypeDao.findMailTypeById(2));
                    mailSentDao.save(email);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }
    /**
     * <p>method called when application is ran</p>
     *  <p>checks if borrowing status is "late" and already renewed, and if so sends mails</p>
     */
    public void checkAndSendDefoLateBorrowings(){
        List<BorrowingBean> borrowings = booksProxy.listBorrowings();
        for(BorrowingBean borrowing:borrowings) {

            //get info and calls proper mailMessage for borrowings that cannot be renewed
            if (borrowing.getBorrowingType().getId() == 4 && borrowing.getRenewed() == true) {
                UserBean user = usersProxy.showUser(borrowing.getIdUser());
                BookBean book = booksProxy.showBook(borrowing.getBook().getId());
                String subject = "Retour du livre " + book.getTitle();
                String text = "Bonjour "+user.getFirstName()+" "+user.getLastName()+
                        "\n\nVous avez emprunté le livre '"+book.getTitle()+"' le "+ borrowing.getBorrowed()+"."+
                        "\nLa date limite de retour était le "+borrowing.getLimitDate()+"."+
                        "\nVous avez déjà renouvelé votre emprunt une fois."+
                        "\nMerci de bien vouloir vous rendre à la bibliothèque, " +
                        "et d'effectuer le retour du livre."+
                        "\n\nA bientôt et bonne lecture, "+
                        "\n\nLa bilibothèque de la ville";
                try{
                    mailService.sendSimpleMessage(user.getEmail(), subject, text);
                    MailSent email = prepareModelMailSent(theDateToday(), user.getEmail(), user.getId(), book.getId(), book.getTitle());
                    email.setMailType(mailTypeDao.findMailTypeById(1));
                    mailSentDao.save(email);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    public MailSent prepareModelMailSent(String sentDate, String emailUser, Integer idUser, Integer idBook, String titleBook){
        MailSent email = new MailSent();
        email.setSentDate(sentDate);
        email.setEmailUser(emailUser);
        email.setIdUser(idUser);
        email.setIdBook(idBook);
        email.setTitleBook(titleBook);
        return email;
    }

    public String theDateToday(){
        ZoneId zone = ZoneId.of("Europe/Paris");
        LocalDate today = LocalDate.now(zone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return today.format(formatter);
    }
}
