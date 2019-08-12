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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
     * <p>One method called in run to call both tasks</p>
     */
    public void checkAndSend(){
        checkAndSendRenewableBorrowings();
        checkAndSendDefoLateBorrowings();
    }
    /**
     * <p>method called when application is ran</p>
     *  <p>checks if borrowing status is "late" and renewable, and if so sends mails</p>
     */
    public void checkAndSendRenewableBorrowings(){
        List<BorrowingBean> borrowings = booksProxy.listBorrowings();
        boolean sentLately = false;
        for(BorrowingBean borrowing:borrowings) {
            //get info and calls proper mailMessage for borrowings that can be renewed
            if (borrowing.getBorrowingType().getId() == 4 && borrowing.getRenewed() == false) {
                //checks if for each late borrowing an email has already been sent in the past week
                List<MailSent> thisMailSentList = mailSentDao.findMailSentByIdBorrowing(borrowing.getId());
                if(!thisMailSentList.isEmpty()){
                    sentLately = lastMailSentIsOneDayOld(thisMailSentList);
                }
                //if last email sent is > 1 week or if no email has been sent for this borrowing, get info and send email
                if (thisMailSentList.isEmpty() || sentLately == false){
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
                        MailSent newMailSent = prepareModelMailSent(localDateToString(theDateToday()),  user.getId(), book.getId(), borrowing.getId());
                        newMailSent.setMailType(mailTypeDao.findMailTypeById(2));
                        mailSentDao.save(newMailSent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("LAST MAIL FOR CAN BE RENEWED BORROWINGS SENT WITHIN THE WEEK");
                }
            }
            else {
                System.out.println("NO BORROWINGS THAT COULD BE RENEWED");
            }
        }


    }
    /**
     * <p>method called when application is ran</p>
     *  <p>checks if borrowing status is "late" and already renewed, and if so sends mails</p>
     */
    public void checkAndSendDefoLateBorrowings(){
        List<BorrowingBean> borrowings = booksProxy.listBorrowings();
        boolean sentYesterday = false;

        for(BorrowingBean borrowing:borrowings) {
            //get each late borrowing that cannot be renewed
            if (borrowing.getBorrowingType().getId() == 4 && borrowing.getRenewed() == true) {
                //checks if for each late borrowing an email has already been sent in the day before
                List<MailSent> thisMailSentList = mailSentDao.findMailSentByIdBorrowing(borrowing.getId());
                if(!thisMailSentList.isEmpty()){
                    sentYesterday = lastMailSentIsOneDayOld(thisMailSentList);
                }
                //if last email sent is > 1 day or if no email has been sent for this borrowing, get info and send email
                if (thisMailSentList.isEmpty() || sentYesterday == false) {
                    UserBean user = usersProxy.showUser(borrowing.getIdUser());
                    BookBean book = booksProxy.showBook(borrowing.getBook().getId());
                    String subject = "Retour du livre " + book.getTitle();
                    String text = "Bonjour " + user.getFirstName() + " " + user.getLastName() +
                            "\n\nVous avez emprunté le livre '" + book.getTitle() + "' le " + borrowing.getBorrowed() + "." +
                            "\nLa date limite de retour était le " + borrowing.getLimitDate() + "." +
                            "\nVous avez déjà renouvelé votre emprunt une fois." +
                            "\nMerci de bien vouloir vous rendre à la bibliothèque, " +
                            "et d'effectuer le retour du livre." +
                            "\n\nA bientôt et bonne lecture, " +
                            "\n\nLa bilibothèque de la ville";
                    try {
                        mailService.sendSimpleMessage(user.getEmail(), subject, text);
                        MailSent newMailSent = prepareModelMailSent(localDateToString(theDateToday()), user.getId(), book.getId(), borrowing.getId());
                        newMailSent.setMailType(mailTypeDao.findMailTypeById(1));
                        mailSentDao.save(newMailSent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("LAST MAIL FOR DEFO LATE BORROWINGS SENT WITHIN THE WEEK");
                }
            }
            else {
                System.out.println("NO DEFO LATE BORROWINGS");
            }
        }
    }

    /**
     * <p>Prepares the build for a MailSent Model, without the mailtype</p>
     * @param sentDate
     * @param idUser
     * @param idBook
     * @param idBorrowing
     * @return model MailSent
     */
    public MailSent prepareModelMailSent(String sentDate, Integer idUser, Integer idBook, Integer idBorrowing){
        MailSent model = new MailSent();
        model.setSentDate(sentDate);
        model.setIdUser(idUser);
        model.setIdBorrowing(idBorrowing);
        model.setIdBook(idBook);
        return model;
    }

    /**
     * <p>Gets LocalDate for today</p>
     * @return localDate
     */
    public LocalDate theDateToday(){
        ZoneId zone = ZoneId.of("Europe/Paris");
        LocalDate today = LocalDate.now(zone);
        return today;
    }
    /**
     * <p>Parses a LocalDate to string</p>
     * @return string
     */
    public String localDateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    /**
     * <p>Parses Date to LocalDate</p>
     * @param dateToConvert
     * @return localDate
     */
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * <p>In a List of MailSent, checks if the last one has been sent within the last week</p>
     * <p>If it has been sent during last week = true</p>
     * <p>If not (longer than one week)  = false</p>
     * @param mailSentList
     * @return boolean
     */
    public boolean lastMailSentIsOneDayOld(List<MailSent> mailSentList){
        boolean sentLately = false;
        for(MailSent mail:mailSentList){
            String sentDate = mail.getSentDate();
            try{
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sentDate);
                LocalDate lastSentDate = convertToLocalDateViaInstant(date);
                ZoneId zone = ZoneId.of("Europe/Paris");
                LocalDate today = LocalDate.now(zone);
                if(lastSentDate.plusDays(1).compareTo(today) >= 0){
                    sentLately = true;
                }
            }catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return sentLately;
    }
}
