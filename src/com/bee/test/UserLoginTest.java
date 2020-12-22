package com.bee.test;

import com.bee.pojo.User;
import com.bee.service.UserLoginService;
import com.bee.service.impl.UserLoginServiceImpl;

public class UserLoginTest {
    public static void main(String[] args) {
        UserLoginService userLoginService=new UserLoginServiceImpl();
        User hjh = userLoginService.userLogin("hjh", "123");
        System.out.println(hjh.getUsername()+"\t"+"b");;
    }
}