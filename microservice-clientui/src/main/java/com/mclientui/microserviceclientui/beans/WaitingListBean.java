package com.mclientui.microserviceclientui.beans;

public class WaitingListBean {
    private Integer id;
    private BookBean book;
    private Integer userId;
    private Integer userPos;

    public WaitingListBean() {
    }

    @Override
    public String toString() {
        return "WaitingListBean{" +
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

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
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
