package com.mbooks.microservicebooks.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "waitinglist")
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
    private Integer userId;

    @Column(name = "user_pos")
    private Integer userPos;



    public WaitingList() {
    }

    @Override
    public String toString() {
        return "WaitingList{" +
                "id=" + id +
                ", book=" + book +
                ", userId=" + userId +
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserPos() {
        return userPos;
    }

    public void setUserPos(Integer userPos) {
        this.userPos = userPos;
    }
}
