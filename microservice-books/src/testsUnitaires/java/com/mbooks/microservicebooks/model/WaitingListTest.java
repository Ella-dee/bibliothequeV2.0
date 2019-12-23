package com.mbooks.microservicebooks.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WaitingListTest {
    private WaitingList waitingList;
    private Book book;
    private Book book2;

    @Before
    public void setUp() throws Exception {
        book = new Book();
        book.setTitle("Les animaux fantastiques");
        book2 = new Book();
        book2.setTitle("L'ennui le plus long");
        
        waitingList = new WaitingList();
        waitingList.setUserId(4);
        waitingList.setUserPos(1);
        waitingList.setBook(book);
        waitingList.setId(4);
    }
    @Test(expected = Test.None.class)
    public void getBook() {
        Assert.assertEquals(book, waitingList.getBook());
        Assert.assertNotEquals(book2, waitingList.getBook());
        waitingList.setBook(book2);
        Assert.assertEquals(book2, waitingList.getBook());
        Assert.assertNotEquals(book, waitingList.getBook());
    }

    @Test(expected = Test.None.class)
    public void getId() {
        Assert.assertEquals((Integer)4, waitingList.getId());
        Assert.assertNotEquals((Integer)2, waitingList.getId());
        waitingList.setId(2);
        Assert.assertEquals((Integer)2, waitingList.getId());
        Assert.assertNotEquals((Integer)4, waitingList.getId());
    }

    @Test(expected = Test.None.class)
    public void getUserId() {
        Assert.assertEquals((Integer)4, waitingList.getUserId());
        Assert.assertNotEquals((Integer)2, waitingList.getUserId());
        waitingList.setUserId(2);
        Assert.assertEquals((Integer)2, waitingList.getUserId());
        Assert.assertNotEquals((Integer)4, waitingList.getUserId());
    }

    @Test(expected = Test.None.class)
    public void getUserPos() {
        Assert.assertEquals((Integer)1, waitingList.getUserPos());
        Assert.assertNotEquals((Integer)2, waitingList.getUserPos());
        waitingList.setUserPos(2);
        Assert.assertEquals((Integer)2, waitingList.getUserPos());
        Assert.assertNotEquals((Integer)1, waitingList.getUserPos());
    }

    @Test(expected = Test.None.class)
    public void toStringT() {
        Assert.assertEquals("WaitingList{id=4, book=Book{id=null, ref='null', title='Les animaux fantastiques'}, userId=4, userPos=1}", waitingList.toString());
        Assert.assertNotEquals("WaitingList{id=null, book=Book{id=2, ref='null', title='L'ennui le plus long'}, userId=2, userPos=2}", waitingList.toString());
    }

}