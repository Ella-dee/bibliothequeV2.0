package com.mmailing.microservicemailing.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MailSentForWaitingListTest {
   
    private MailSentForWaitingList mail;

    @Before
    public void setUp() throws Exception {
        mail = new MailSentForWaitingList();
        mail.setIdBook(1);
        mail.setIdUser(2);
        mail.setSentDate("23/12/2019");
    }
    @Test(expected = Test.None.class)
    public void getSentDate() {
        Assert.assertEquals("23/12/2019", mail.getSentDate());
        Assert.assertNotEquals("01/01/2019", mail.getSentDate());
        mail.setSentDate("21/01/2019");
        Assert.assertEquals("21/01/2019", mail.getSentDate());
        Assert.assertNotEquals("23/12/2019", mail.getSentDate());
        
    }

    @Test(expected = Test.None.class)
    public void getIdUser() {
        Assert.assertEquals((Integer)2, mail.getIdUser());
        Assert.assertNotEquals((Integer)4, mail.getIdUser());
        mail.setIdUser(4);
        Assert.assertEquals((Integer)4, mail.getIdUser());
        Assert.assertNotEquals((Integer)2, mail.getIdUser());
    }

    @Test(expected = Test.None.class)
    public void getIdBook() {
        Assert.assertEquals((Integer)1, mail.getIdBook());
        Assert.assertNotEquals((Integer)2, mail.getIdBook());
        mail.setIdBook(2);
        Assert.assertEquals((Integer)2, mail.getIdBook());
        Assert.assertNotEquals((Integer)1, mail.getIdBook());
    }
}