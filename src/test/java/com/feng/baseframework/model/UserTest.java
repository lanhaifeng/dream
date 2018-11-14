package com.feng.baseframework.model;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void nestedClass() {
        User user = new User.Builder().withUserName("tom").withName("张三").build();
        Assert.assertEquals("User{id=, userName='tom', name='张三', password=''}", user.toString());
    }
}