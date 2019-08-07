package com.mmailing.microservicemailing.dao;

import com.mmailing.microservicemailing.model.MailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailTypeDao extends JpaRepository<MailType, Integer> {
    MailType findMailTypeById(Integer id);
}
