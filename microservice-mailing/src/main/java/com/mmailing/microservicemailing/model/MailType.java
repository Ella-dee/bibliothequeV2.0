package com.mmailing.microservicemailing.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "mailing_types")
public class MailType {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType. IDENTITY )
    private Integer id;

    @NotBlank
    @Column(name="type")
    private String type;

    @JsonSerialize(using = MailSentListSerializer.class)
    @OneToMany (mappedBy="mailType")
    private List<MailSent> mailSentList;

    public MailType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MailSent> getMailSentList() {
        return mailSentList;
    }

    public void setMailSentList(List<MailSent> mailSentList) {
        this.mailSentList = mailSentList;
    }
}
