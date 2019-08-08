package com.mmailing.microservicemailing.beans;

public class BookBean {

    private Integer id;
    private String ref;
    private String title;
    private String editionHouse;
    private String releaseDate;
    private Integer pages;
    private String synopsis;
    private String bookCover;
    private Boolean available;

    private CategoryBean category;
    private AuthorBean author;

    public BookBean() {
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
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

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }


    @Override
    public String toString() {
        return "BookBean{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
