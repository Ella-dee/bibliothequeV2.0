package com.mclientui.microserviceclientui.beans;


public class CategoryBean {
    private Integer id;
    private String name;
    //private List<BookBean>books;

    public CategoryBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "CategoryBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
