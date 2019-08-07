package com.mmailing.microservicemailing.dao;

import com.mmailing.microservicemailing.model.MailSent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailSentDao extends JpaRepository<MailSent, Integer> {
}
