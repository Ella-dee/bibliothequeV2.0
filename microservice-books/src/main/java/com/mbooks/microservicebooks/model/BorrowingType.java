package com.mbooks.microservicebooks.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "borrowing_types")
//@JsonSerialize(using= BorrowingTypeSerializer.class)
public class BorrowingType {
    @Id
    @Column (name="id")
    @GeneratedValue(strategy = GenerationType. IDENTITY )
    private Integer id;

    @Column (name="type", unique=true)
    private String type;

    @JsonSerialize(using = BorrowingListSerializer.class)
    @OneToMany (mappedBy="borrowingType")
    private List<Borrowing> borrowingList;

    public BorrowingType() {
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

    public List<Borrowing> getBorrowingList() {
        return borrowingList;
    }

    public void setBorrowingList(List<Borrowing> borrowingList) {
        this.borrowingList = borrowingList;
    }

    @Override
    public String toString() {
        return "BorrowingType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
