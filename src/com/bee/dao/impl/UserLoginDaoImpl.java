package com.bee.dao.impl;

import com.bee.commons.JdbcUtils;
import com.bee.dao.UserLoginDao;
import com.bee.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginDaoImpl implements UserLoginDao {
    @Override
    public User selectUsersByUserNameAndUserPwd(String username, String userpwd) {
        Connection conn;
        User user = null;
        try {
            conn = JdbcUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("select * from users where username=? and userpwd=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userpwd);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setUserpwd(resultSet.getString("userpwd"));
                user.setMember(resultSet.getInt("member"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}