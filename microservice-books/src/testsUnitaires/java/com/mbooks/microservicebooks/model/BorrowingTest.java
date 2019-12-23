package com.mbooks.microservicebooks.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class BorrowingTest {
    
    private Borrowing borrowing;
    private Book book;
    private Book book2;
    private BorrowingType borrowingType;
    private BorrowingType borrowingType2;

    @Before
    public void setUp() throws Exception {
        borrowing = new Borrowing();
        book = new Book();

        book = new Book();
        book.setTitle("Les animaux fantastiques");
        book2 = new Book();
        book2.setTitle("L'ennui le plus long");

        borrowingType = new BorrowingType();
        borrowingType.setType("Fantastique");
        borrowingType2 = new BorrowingType();
        borrowingType2.setType("Ennuyant");
        
        borrowing.setRenewed(false);
        borrowing.setBorrowed("23/11/2019");
        borrowing.setLimitDate("23/12/2019");
        borrowing.setReturned(null);
        borrowing.setIdUser(4);
        borrowing.setBorrowingType(borrowingType);
        borrowing.setBook(book);
    }

    @Test(expected = Test.None.class)
    public void getRenewed() {
        Assert.assertEquals(false, borrowing.getRenewed());
        Assert.assertNotEquals(true, borrowing.getRenewed());
        borrowing.setRenewed(true);
        Assert.assertEquals(true, borrowing.getRenewed());
        Assert.assertNotEquals(false, borrowing.getRenewed());
    }

    @Test(expected = Test.None.class)
    public void getLimitDate() {
        Assert.assertEquals("23/12/2019", borrowing.getLimitDate());
        Assert.assertNotEquals("23/11/2019", borrowing.getLimitDate());
        borrowing.setLimitDate("23/11/2020");
        Assert.assertEquals("23/11/2020", borrowing.getLimitDate());
        Assert.assertNotEquals("23/12/2019", borrowing.getLimitDate());
    }

    @Test(expected = Test.None.class)
    public void getBorrowed() {
        Assert.assertEquals("23/11/2019", borrowing.getBorrowed());
        Assert.assertNotEquals("23/12/2019", borrowing.getBorrowed());
        borrowing.setBorrowed("23/11/2020");
        Assert.assertEquals("23/11/2020", borrowing.getBorrowed());
        Assert.assertNotEquals("23/11/2019", borrowing.getBorrowed());
    }

    @Test(expected = Test.None.class)
    public void getReturned() {
        Assert.assertEquals(null, borrowing.getReturned());
        Assert.assertNotEquals("23/12/2019", borrowing.getReturned());
        borrowing.setReturned("12/12/2019");
        Assert.assertEquals("12/12/2019", borrowing.getReturned());
        Assert.assertNotEquals(null, borrowing.getReturned());
    }

    @Test(expected = Test.None.class)
    public void getIdUser() {
        Assert.assertEquals((Integer)4, borrowing.getIdUser());
        Assert.assertNotEquals((Integer)2, borrowing.getIdUser());
        borrowing.setIdUser(2);
        Assert.assertEquals((Integer)2, borrowing.getIdUser());
        Assert.assertNotEquals((Integer)4, borrowing.getIdUser());
    }

    @Test(expected = Test.None.class)
    public void getBook() {  
        Assert.assertEquals(book, borrowing.getBook());
        Assert.assertNotEquals(book2, borrowing.getBook());
        borrowing.setBook(book2);
        Assert.assertEquals( book2, borrowing.getBook());
        Assert.assertNotEquals(book, borrowing.getBook());
    }

    @Test(expected = Test.None.class)
    public void getBorrowingType() {
        Assert.assertEquals(borrowingType, borrowing.getBorrowingType());
        Assert.assertNotEquals(borrowingType2, borrowing.getBorrowingType());
        borrowing.setBorrowingType(borrowingType2);
        Assert.assertEquals(borrowingType2, borrowing.getBorrowingType());
        Assert.assertNotEquals(borrowingType, borrowing.getBorrowingType());
    }

    @Test(expected = Test.None.class)
    public void toStringT() {
        Assert.assertEquals("Borrowing{id=null, borrowed=23/11/2019, returned=null}", borrowing.toString());
        Assert.assertNotEquals("Borrowing{id=1, borrowed=23/11/2019, returned=12/12/2019}", borrowing.toString());
    }
}