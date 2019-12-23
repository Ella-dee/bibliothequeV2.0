package com.musers.microserviceusers.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoleTest {

    private Role role;
    private User user;
    private User user2;

    @Before
    public void setUp() throws Exception {
        role = new Role();
        user = new User();
        user2 = new User();

        user.setFirstName("Newton");
        user2.setLastName("Scamender");
        List<User> userList = new ArrayList<>();
        userList.add(user);

        role.setRoleName("TEST ROLE");
        role.setUsers(userList);
    }

    @Test(expected = Test.None.class)
    public void getUsers() {
        Assert.assertEquals("[User{id=null, firstName='Newton', lastName='null'}]", role.getUsers().toString());
        Assert.assertNotEquals(null, role.getUsers().toString());
        role.getUsers().add(user2);
        Assert.assertEquals("[User{id=null, firstName='Newton', lastName='null'}, User{id=null, firstName='null', lastName='Scamender'}]", role.getUsers().toString());
        Assert.assertNotEquals("[User{id=null, firstName='Newton', lastName='null'}]", role.getUsers().toString());
    }

    @Test(expected = Test.None.class)
    public void getRoleName() {
        Assert.assertEquals("TEST ROLE", role.getRoleName());
        Assert.assertNotEquals(null, role.getRoleName());
        role.setRoleName("STORMAGEDDON MASTER OF ALL");
        Assert.assertEquals("STORMAGEDDON MASTER OF ALL", role.getRoleName());
        Assert.assertNotEquals("TEST ROLE", role.getRoleName());
    }
}