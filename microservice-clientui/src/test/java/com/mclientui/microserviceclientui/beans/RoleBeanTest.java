package com.mclientui.microserviceclientui.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoleBeanTest {

    private RoleBean role;

    @Before
    public void setUp() throws Exception {
        role = new RoleBean();
        role.setRoleName("TEST ROLE");
        role.setId(1);
    }

    @Test(expected = Test.None.class)
    public void getId() {
        Assert.assertEquals((Integer)1, role.getId());
        Assert.assertNotEquals(null, role.getId());
        role.setId(2);
        Assert.assertEquals((Integer)2, role.getId());
        Assert.assertNotEquals((Integer)1, role.getId());
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