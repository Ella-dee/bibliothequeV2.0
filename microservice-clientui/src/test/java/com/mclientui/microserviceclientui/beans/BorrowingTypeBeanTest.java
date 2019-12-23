package com.mclientui.microserviceclientui.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BorrowingTypeBeanTest {

    private BorrowingTypeBean borrowingType;

    @Before
    public void setUp() throws Exception {
        borrowingType = new BorrowingTypeBean();
        borrowingType.setType("Fantastique");
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
}