package com.mclientui.microserviceclientui.beans;

public class WaitingListBean {
    private Integer id;
    private BookBean book;
    private Integer idUser;
    private Integer userPos;

    public WaitingListBean() {
    }

    @Override
    public String toString() {
        return "WaitingListBean{" +
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

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
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
