package com.bee.dao.impl;

import com.bee.commons.JdbcUtils;
import com.bee.dao.UserRegisterDao;
import com.bee.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegisterDaoImpl implements UserRegisterDao {
    @Override
    public void insertUser(User users) {
        Connection conn ;
        conn = JdbcUtils.getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement("insert into users values(default ,?,?,? )");
            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getUserpwd());
            preparedStatement.setInt(3, users.getMember());
            preparedStatement.execute();
            String tablename = users.getUsername();
            PreparedStatement createTableByUserId = conn.prepareStatement("create table " + tablename + "(id int(15) not null auto_increment primary key,file_name varchar(30),file_type varchar(10))");
            createTableByUserId.execute();
            conn.commit();
        } catch (SQLException throwables) {
            JdbcUtils.rollback(conn);
            throwables.printStackTrace();
        }
    }
}