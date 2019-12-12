package com.mmailing.microservicemailing.dao;

import com.mmailing.microservicemailing.beans.WaitingListBean;
import com.mmailing.microservicemailing.mailing.MailService;
import com.mmailing.microservicemailing.model.MailSentForWaitingList;
import com.mmailing.microservicemailing.proxies.MicroserviceBooksProxy;
import com.mmailing.microservicemailing.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mmailing.microservicemailing.utils.DateUtils.localDateToString;
import static com.mmailing.microservicemailing.utils.DateUtils.theDateToday;

@Service
@Transactional
public class MailSentForWaitingListService {

    @Autowired
    private MicroserviceBooksProxy booksProxy;
    @Autowired
    private MicroserviceUsersProxy usersProxy;
    @Autowired
    private MailService mailService;
    @Autowired
    private MailSentForWaitingListDao mailSentForWaitingListDao;

    public void sendNotifNextInLine(MailSentForWaitingList mail){
        //Delete the line for user/book in db
        booksProxy.cancelWaitingList(mail.getId());
        //Search if there's other people waiting for this book
        List<WaitingListBean> list = booksProxy.listWaitingLists();
        List<WaitingListBean> listForThisBook = new ArrayList<>();
        for(WaitingListBean item: list){
            if(item.getBook().getId() == mail.getIdBook()){
                listForThisBook.add(item);
            }
        }
        //if there is, send a notification to the next in line
        if (listForThisBook.size()>0){
            ArrayList<Integer> idList = new ArrayList<>();
            for (WaitingListBean item: listForThisBook){
                idList.add(item.getId());
            }
            Collections.sort(idList);
            WaitingListBean waitingList = booksProxy.showWaitingList(idList.get(0));
            //Delete from db the initial mail
            mailSentForWaitingListDao.delete(mail);
            //send
            sendNotif(waitingList.getIdUser(), waitingList.getBook().getId(), usersProxy.showUser(waitingList.getIdUser()).getEmail());
        }
    }

    /**
     * <h2>send an email to someone in a waiting list</h2>
     * <p>called from MailSentController.sendNotifWhenAwaitedBookIsReturned &&
     * MailSentForWaitingListService.sendNotifNextInLine</p>
     * @param userId
     * @param bookId
     * @param userEmail
     */
    public void sendNotif(Integer userId, Integer bookId, String userEmail) {
        String subject = "Un Livre que vous attendez est disponible";
        String message = "Vous avez la priorité sur ce livre pendant 48H, après ce délai vous serez retiré de la liste d'attente\n";
        try {
            mailService.sendSimpleMessage(userEmail, subject, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MailSentForWaitingList mailSentForWaitingList = new MailSentForWaitingList();
        mailSentForWaitingList.setSentDate(localDateToString(theDateToday()));
        mailSentForWaitingList.setIdBook(bookId);
        mailSentForWaitingList.setIdUser(userId);
        mailSentForWaitingListDao.save(mailSentForWaitingList);
    }

}
