package com.bee.test;

import com.bee.pojo.User;
import com.bee.service.UserRegisterService;
import com.bee.service.impl.UserRegisterServiceImpl;

public class UserRegisterTest {
    public static void main(String[] args) {
        User user=new User();
        user.setUsername("wsqwww");
        user.setUserpwd("66ww6");
        UserRegisterService UserRegisterService=new UserRegisterServiceImpl();
        UserRegisterService.addUser(user);
    }
}