package com.mbooks.microservicebooks.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "waitinglist")
@JsonSerialize(using= WaitingListSerializer.class)
public class WaitingList {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType. IDENTITY )
    private Integer id;

    @ManyToOne
    @JoinColumn(name="id_book")
    private Book book;

    @NotNull
    @Column(name="id_user")
    private Integer idUser;

    @Column(name = "user_pos")
    private Integer userPos;



    public WaitingList() {
    }

    @Override
    public String toString() {
        return "WaitingList{" +
                "id=" + id +
                ", book=" + book +
                ", idUser=" + idUser +
                ", userPos=" + userPos +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getUserPos() {
        return userPos;
    }

    public void setUserPos(Integer userPos) {
        this.userPos = userPos;
    }
}
