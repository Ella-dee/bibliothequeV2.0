package com.mbooks.microservicebooks.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;


public class CategoryTest {

    private Category cat;
    private Book book;
    private Book book2;
    
    @Before
    public void setUp() throws Exception {
        book = new Book();
        book.setTitle("Les animaux fantastiques");
        book2 = new Book();
        book2.setTitle("L'ennui le plus long");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        
        cat = new Category();
        cat.setName("Fantastique");
        cat.setBooks(bookList);
        cat.setId(1);
    }

    @Test(expected = Test.None.class)
    public void getId() {
        Assert.assertEquals((Integer)1, cat.getId());
        Assert.assertNotEquals(null, cat.getId());
        cat.setId(2);
        Assert.assertEquals((Integer)2, cat.getId());
        Assert.assertNotEquals((Integer)1, cat.getId());
    }

    @Test(expected = Test.None.class)
    public void getName() {
        Assert.assertEquals("Fantastique", cat.getName());
        Assert.assertNotEquals("Ennuyant", cat.getName());
        cat.setName("Ennuyant");
        Assert.assertEquals("Ennuyant", cat.getName());
        Assert.assertNotEquals("Fantastique", cat.getName());
    }

    @Test(expected = Test.None.class)
    public void getBooks() {
        Assert.assertEquals("[Book{id=null, ref='null', title='Les animaux fantastiques'}]", cat.getBooks().toString());
        Assert.assertNotEquals("[Book{id=null, ref='null', title='Le flux des particules'}]", cat.getBooks().toString());
        cat.getBooks().add(book2);
        Assert.assertEquals("[Book{id=null, ref='null', title='Les animaux fantastiques'}, Book{id=null, ref='null', title='L'ennui le plus long'}]", cat.getBooks().toString());
        Assert.assertNotEquals("[Book{id=null, ref='null', title='Les animaux fantastiques'}]", cat.getBooks().toString());
    }

    @Test(expected = Test.None.class)
    public void toStringT() {
        Assert.assertEquals("Category{id=1, name='Fantastique'}", cat.toString());
        Assert.assertNotEquals("Category{id=null, name='Ennuyant'}", cat.toString());
    }
}