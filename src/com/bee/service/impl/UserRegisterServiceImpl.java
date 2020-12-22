package com.bee.service.impl;

import com.bee.dao.UserRegisterDao;
import com.bee.dao.impl.UserRegisterDaoImpl;
import com.bee.pojo.User;
import com.bee.service.UserRegisterService;

public class UserRegisterServiceImpl implements UserRegisterService {
    @Override
    public void addUser(User users) {
        UserRegisterDao userManagementDao=new UserRegisterDaoImpl();
        userManagementDao.insertUser(users);
    }
}