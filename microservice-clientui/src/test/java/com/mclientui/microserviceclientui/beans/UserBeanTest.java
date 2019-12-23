package com.mclientui.microserviceclientui.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserBeanTest {
    
    private RoleBean role;
    private RoleBean role2;
    private UserBean user;

    @Before
    public void setUp() throws Exception {
        role = new RoleBean();
        role.setRoleName("STORMAGEDDON MASTER OF ALL");
        role2 = new RoleBean();
        role2.setRoleName("FIRST CLASS AUROR");

        user = new UserBean();
        user.setId(1);
        user.setFirstName("Newton");
        user.setLastName("Scamender");
        user.setEmail("auror@mom.co.uk");
        user.setUserRole(role);
        user.setPassword("fantasticbeasts");
        user.setUserName("Newt1934");
    }
    @Test(expected = Test.None.class)
    public void getId() {
        Assert.assertEquals((Integer)1, user.getId());
        Assert.assertNotEquals(null, user.getId());
        user.setId(2);
        Assert.assertEquals((Integer)2, user.getId());
        Assert.assertNotEquals((Integer)1, user.getId());
    }
    
    @Test(expected = Test.None.class)
    public void getUserName() {
        Assert.assertEquals("Newt1934", user.getUserName());
        Assert.assertNotEquals("Newton", user.getUserName());
        user.setUserName("Tina1942");
        Assert.assertEquals("Tina1942", user.getUserName());
        Assert.assertNotEquals("Newt1934", user.getUserName());
    }

    @Test(expected = Test.None.class)
    public void getFirstName() {
        Assert.assertEquals("Newton", user.getFirstName());
        Assert.assertNotEquals("James", user.getFirstName());
        user.setFirstName("James");
        Assert.assertEquals("James", user.getFirstName());
        Assert.assertNotEquals("Newton", user.getFirstName());
    }

    @Test(expected = Test.None.class)
    public void getLastName() {
        Assert.assertEquals("Scamender", user.getLastName());
        Assert.assertNotEquals("Redmaine", user.getLastName());
        user.setLastName("Redmaine");
        Assert.assertEquals("Redmaine", user.getLastName());
        Assert.assertNotEquals("Scamender", user.getLastName());
    }

    @Test(expected = Test.None.class)
    public void getEmail() {
        Assert.assertEquals("auror@mom.co.uk", user.getEmail());
        Assert.assertNotEquals("test@gmail.com", user.getEmail());
        user.setEmail("test@gmail.com");
        Assert.assertEquals("test@gmail.com", user.getEmail());
        Assert.assertNotEquals("auror@mom.co.uk", user.getEmail());
    }

    @Test(expected = Test.None.class)
    public void getPassword() {
        Assert.assertEquals("fantasticbeasts", user.getPassword());
        Assert.assertNotEquals("password", user.getPassword());
        user.setPassword("password");
        Assert.assertEquals("password", user.getPassword());
        Assert.assertNotEquals("fantasticbeasts", user.getPassword());
    }

    @Test(expected = Test.None.class)
    public void getUserRole() {
        Assert.assertEquals("STORMAGEDDON MASTER OF ALL", user.getUserRole().getRoleName());
        Assert.assertNotEquals("FIRST CLASS AUROR", user.getUserRole().getRoleName());
        user.setUserRole(role2);
        Assert.assertEquals("FIRST CLASS AUROR",user.getUserRole().getRoleName());
        Assert.assertNotEquals("STORMAGEDDON MASTER OF ALL", user.getUserRole().getRoleName());
    }

   
    @Test(expected = Test.None.class)
    public void getResetToken() {
        Assert.assertEquals(null, user.getResetToken());
        Assert.assertNotEquals("UKDEZFKJZEOF", user.getResetToken());
        user.setResetToken("SKQLZRIEZlFRZO");
        Assert.assertEquals("SKQLZRIEZlFRZO", user.getResetToken());
        Assert.assertNotEquals("UKDEZFKJZEOF", user.getResetToken());
    }

}