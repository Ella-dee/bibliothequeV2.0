package com.mbooks.microservicebooks.model;

import javax.persistence.*;

@Entity
@Table(name = "borrowings")
public class Borrowing {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType. IDENTITY )
    private Integer id;

    @Column (name="date_borrowed")
    private String borrowed;

    @Column (name="date_limit")
    private String limitDate;

    @Column (name="date_returned")
    private String returned;

    @Column(name="reminder_mail")
    private Boolean reminderMail;

    @ManyToOne
    @JoinColumn(name="id_book")
    private Book book;

    @ManyToOne
    @JoinColumn(name="id_type")
    private BorrowingType borrowingType;

    @Column(name="id_user")
    private Integer idUser;

    public Borrowing() {
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(String borrowed) {
        this.borrowed = borrowed;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }

    public Boolean getReminderMail() {
        return reminderMail;
    }

    public void setReminderMail(Boolean reminderMail) {
        this.reminderMail = reminderMail;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BorrowingType getBorrowingType() {
        return borrowingType;
    }

    public void setBorrowingType(BorrowingType borrowingType) {
        this.borrowingType = borrowingType;
    }

    @Override
    public String toString() {
        return "Borrowing{" +
                "id=" + id +
                ", borrowed=" + borrowed +
                ", returned=" + returned +
                '}';
    }
}
