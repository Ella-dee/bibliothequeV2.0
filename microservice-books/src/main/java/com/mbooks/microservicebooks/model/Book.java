package com.mbooks.microservicebooks.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "books")
@JsonIgnoreProperties(value = {"borrowingList"})
public class Book {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType. IDENTITY )
    private Integer id;

    @NotBlank
    @Column(name="ref")
    private String ref;

    @NotBlank
    @Column(name="title")
    private String title;

    @Column(name = "image")
    private String bookCover;

    @NotBlank
    @Column(name="edition")
    private String editionHouse;

    @Column(name="release_date")
    private String releaseDate;

    @Column(name="pages")
    private Integer pages;

    @Column(name="nbr")
    private Integer nbr;

    @Column(name="synopsis")
    @Size(max = 600, message = "maximum 600 caractères")
    private String synopsis;

    @ManyToOne
    @JoinColumn(name="id_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name="id_author")
    private Author author;

    @OneToMany(mappedBy="book")
    private List<Borrowing> borrowingList;

    @Transient
    private Boolean available;

    @Transient
    private int availableBooksNbr;

    public Book() {
    }

    public Boolean getAvailable() {

        return available;
    }

    public Integer getNbr() {
        return nbr;
    }

    public void setNbr(Integer nbr) {
        this.nbr = nbr;
    }

    public int getAvailableBooksNbr() {
        return availableBooksNbr;
    }

    public void setAvailableBooksNbr(int availableBooksNbr) {
        this.availableBooksNbr = availableBooksNbr;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<Borrowing> getBorrowingList() {
        return borrowingList;
    }

    public void setBorrowingList(List<Borrowing> borrowingList) {
        this.borrowingList = borrowingList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEditionHouse() {
        return editionHouse;
    }

    public void setEditionHouse(String editionHouse) {
        this.editionHouse = editionHouse;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
