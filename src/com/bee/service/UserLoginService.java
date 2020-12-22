package com.bee.service;

import com.bee.pojo.User;

public interface UserLoginService {
    User userLogin(String username, String userpwd);
}
