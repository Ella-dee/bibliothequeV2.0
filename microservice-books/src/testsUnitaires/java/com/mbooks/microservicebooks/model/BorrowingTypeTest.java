package com.mbooks.microservicebooks.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class BorrowingTypeTest {
    private BorrowingType borrowingType;
    private Borrowing b;
    private Borrowing b2;
    private Book book;
    private Book book2;

    @Before
    public void setUp() throws Exception {
        borrowingType = new BorrowingType();
        borrowingType.setType("Fantastique");
        
        book = new Book();
        book.setTitle("Les animaux fantastiques");
        book2 = new Book();
        book2.setTitle("L'ennui le plus long");

        b = new Borrowing();
        b2 = new Borrowing();
        b.setBorrowingType(borrowingType);
        b.setId(1);
        b.setBorrowed("12/12/2019");
        b2.setBorrowingType(borrowingType);
        b2.setId(2);
        b2.setBorrowed("01/01/2018");

        List<Borrowing> borrowingList = new ArrayList<>();
        borrowingList.add(b);

        borrowingType.setBorrowingList(borrowingList);
        borrowingType.setId(1);
    }

    @Test(expected = Test.None.class)
    public void getId() {
        Assert.assertEquals((Integer)1, borrowingType.getId());
        Assert.assertNotEquals(null, borrowingType.getId());
        borrowingType.setId(2);
        Assert.assertEquals((Integer)2, borrowingType.getId());
        Assert.assertNotEquals((Integer)1, borrowingType.getId());
    }

    @Test(expected = Test.None.class)
    public void getType() {
        Assert.assertEquals("Fantastique", borrowingType.getType());
        Assert.assertNotEquals("Ennuyant", borrowingType.getType());
        borrowingType.setType("Ennuyant");
        Assert.assertEquals("Ennuyant", borrowingType.getType());
        Assert.assertNotEquals("Fantastique", borrowingType.getType());
    }

    @Test(expected = Test.None.class)
    public void getBorrowingList() {
        Assert.assertEquals("[Borrowing{id=1, borrowed=12/12/2019, returned=null}]", borrowingType.getBorrowingList().toString());
        Assert.assertNotEquals("[Borrowing{id=2, borrowed=null, returned=null}]", borrowingType.getBorrowingList().toString());
        borrowingType.getBorrowingList().add(b2);
        Assert.assertEquals("[Borrowing{id=1, borrowed=12/12/2019, returned=null}, Borrowing{id=2, borrowed=01/01/2018, returned=null}]", borrowingType.getBorrowingList().toString());
        Assert.assertNotEquals("[Borrowing{id=1, borrowed=01/01/2018, returned=null}]", borrowingType.getBorrowingList().toString());
    }

    @Test(expected = Test.None.class)
    public void toStringT() {
        Assert.assertEquals("BorrowingType{id=1, type='Fantastique'}", borrowingType.toString());
        Assert.assertNotEquals("BorrowingType{id=null, type='Ennuyant'}", borrowingType.toString());
    }
}