package com.bee.test;

import com.bee.commons.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class inTest {
    public static void main(String[] args) throws SQLException {
        String fileName = "aaa.txt";

        String fileType = fileName.substring(fileName.lastIndexOf(".")).substring(1);
        System.out.println(fileType);
    }
}