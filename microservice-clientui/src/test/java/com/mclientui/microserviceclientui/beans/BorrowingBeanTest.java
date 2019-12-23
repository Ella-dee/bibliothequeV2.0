package com.mclientui.microserviceclientui.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BorrowingBeanTest {

    private BorrowingBean borrowing;
    private BookBean book;
    private BookBean book2;
    private BorrowingTypeBean borrowingType;
    private BorrowingTypeBean borrowingType2;

    @Before
    public void setUp() throws Exception {
        borrowing = new BorrowingBean();
        book = new BookBean();

        book = new BookBean();
        book.setTitle("Les animaux fantastiques");
        book2 = new BookBean();
        book2.setTitle("L'ennui le plus long");

        borrowingType = new BorrowingTypeBean();
        borrowingType.setType("Fantastique");
        borrowingType2 = new BorrowingTypeBean();
        borrowingType2.setType("Ennuyant");

        borrowing.setRenewed(false);
        borrowing.setId(4);
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
    public void getId() {
        Assert.assertEquals((Integer)4, borrowing.getId());
        Assert.assertNotEquals((Integer)2, borrowing.getId());
        borrowing.setId(2);
        Assert.assertEquals((Integer)2, borrowing.getId());
        Assert.assertNotEquals((Integer)4, borrowing.getId());
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

}