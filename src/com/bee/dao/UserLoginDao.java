package com.bee.dao;

import com.bee.pojo.User;

public interface UserLoginDao {
    public User selectUsersByUserNameAndUserPwd(String username, String userpwd);
}
