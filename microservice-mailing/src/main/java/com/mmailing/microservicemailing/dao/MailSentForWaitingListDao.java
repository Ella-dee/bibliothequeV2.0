package com.mmailing.microservicemailing.dao;

import com.mmailing.microservicemailing.model.MailSentForWaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailSentForWaitingListDao extends JpaRepository<MailSentForWaitingList, Integer> {
    List<MailSentForWaitingList> findMailSentByIdUser(Integer userid);
    List<MailSentForWaitingList> findMailSentByIdBook(Integer bookid);
}
