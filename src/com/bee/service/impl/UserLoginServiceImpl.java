package com.bee.service.impl;

import com.bee.dao.UserLoginDao;
import com.bee.dao.impl.UserLoginDaoImpl;
import com.bee.exception.UserNotFoundException;
import com.bee.pojo.User;
import com.bee.service.UserLoginService;

public class UserLoginServiceImpl implements UserLoginService {
    @Override
    public User userLogin(String username, String userpwd) {
        UserLoginDao userLoginDao=new UserLoginDaoImpl();
        User user = userLoginDao.selectUsersByUserNameAndUserPwd(username, userpwd);
        if (user == null) {
            throw new UserNotFoundException("用户名或密码有误！");
        }
        return user;
    }
}