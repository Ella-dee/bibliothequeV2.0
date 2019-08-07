package com.mmailing.microservicemailing.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "mailings")
public class MailSent {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType. IDENTITY )
    private Integer id;

    @NotBlank
    @Column(name="sent_date")
    private String sentDate;

    @Column(name="id_user")
    private Integer idUser;

    @Column(name="id_book")
    private Integer idBook;

    @Column(name="id_borrowing")
    private Integer idBorrowing;

    @ManyToOne
    @JoinColumn(name="id_type")
    private MailType mailType;

    public MailSent() {
    }

    public Integer getId() {
        return id;
    }

    public MailType getMailType() {
        return mailType;
    }

    public void setMailType(MailType mailType) {
        this.mailType = mailType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdBorrowing() {
        return idBorrowing;
    }

    public void setIdBorrowing(Integer idBorrowing) {
        this.idBorrowing = idBorrowing;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

}
