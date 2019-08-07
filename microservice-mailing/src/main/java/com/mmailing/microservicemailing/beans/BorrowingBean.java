package com.mmailing.microservicemailing.beans;

public class BorrowingBean {

    private Integer id;
    private String borrowed;
    private String limitDate;
    private String returned;
    private Boolean renewed;
    private BookBean book;
    private BorrowingTypeBean borrowingType;

    private Integer idUser;

    public BorrowingBean() {
    }

    public Boolean getRenewed() {
        return renewed;
    }

    public void setRenewed(Boolean renewed) {
        this.renewed = renewed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
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

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public BorrowingTypeBean getBorrowingType() {
        return borrowingType;
    }

    public void setBorrowingType(BorrowingTypeBean borrowingType) {
        this.borrowingType = borrowingType;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
