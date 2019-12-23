package com.mclientui.microserviceclientui.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WaitingListBeanTest {

    private WaitingListBean waitingList;
    private BookBean book;
    private BookBean book2;

    @Before
    public void setUp() throws Exception {
        book = new BookBean();
        book.setTitle("Les animaux fantastiques");
        book2 = new BookBean();
        book2.setTitle("L'ennui le plus long");

        waitingList = new WaitingListBean();
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
        Assert.assertEquals("WaitingListBean{id=4, book=BookBean{id=null, ref='null', title='Les animaux fantastiques'}, userId=4, userPos=1}", waitingList.toString());
        Assert.assertNotEquals("WaitingListBean{id=null, book=BookBean{id=2, ref='null', title='L'ennui le plus long'}, userId=2, userPos=2}", waitingList.toString());
    }
}