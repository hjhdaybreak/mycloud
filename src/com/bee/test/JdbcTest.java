package com.bee.test;



import com.bee.commons.JdbcUtils;
import com.bee.pojo.TextFile;
import com.bee.service.CloudResourceManagementService;
import com.bee.service.impl.CloudResourceManagementServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {
    public static void main(String[] args) throws SQLException {
        CloudResourceManagementService cloudResourceManagementService=new CloudResourceManagementServiceImpl();
        TextFile aaa = cloudResourceManagementService.findTextFileByUserIdAndFileName(1, "aaa");
        System.out.println(aaa.getFileContent());
    }
}