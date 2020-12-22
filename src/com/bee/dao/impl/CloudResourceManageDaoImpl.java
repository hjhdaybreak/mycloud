package com.bee.dao.impl;

import com.bee.commons.JdbcUtils;
import com.bee.dao.CloudResourceManageDao;
import com.bee.pojo.BinaryFile;
import com.bee.pojo.TextFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CloudResourceManageDaoImpl implements CloudResourceManageDao {
    private void creatBlobTable(Connection conn, String tableName) {
        try {
            conn.setAutoCommit(false);
            PreparedStatement createTable = conn.prepareStatement("create table " + tableName + "( id int(15) not null auto_increment primary key,file_name varchar (30),content MediumBlob)");
            createTable.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            JdbcUtils.rollback(conn);
        }
    }

    private String getUserNameById(Connection conn, int userid) {
        String username = null;
        try {
            PreparedStatement selectUsernameById = conn.prepareStatement("select username from users where userid=?");
            selectUsernameById.setInt(1, userid);
            ResultSet resultSet = selectUsernameById.executeQuery();
            resultSet.next();
            username = resultSet.getString("username");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return username;
    }

    private void creatTxtTable(Connection conn, String tableName) {
        try {
            conn.setAutoCommit(false);
            PreparedStatement createTable = conn.prepareStatement("create table " + tableName + "( id int(15) not null auto_increment primary key,file_name varchar (30),content text)");
            createTable.execute();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JdbcUtils.rollback(conn);
        }
    }

    private boolean fileTableNotExist(Connection conn, String tableName) {
        try {
            ResultSet tables = conn.getMetaData().getTables(null, null, tableName, null);
            if (!tables.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> selectFileByUserIdAndType(int userid, String type) {
        List<String> list;
        if ("document".equals(type)) {
            list = findDocumentById(userid);
        } else
            list = findPictureById(userid);
        return list;
    }

    private List<String> findPictureById(int userid) {
        List<String> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String username = getUserNameById(conn, userid);
            PreparedStatement selectFileNameByType = conn.prepareStatement("select file_name from " + username + " where  file_type in (?,?,?,?) ");
            selectFileNameByType.setString(1, "png");
            selectFileNameByType.setString(2, "GIF");
            selectFileNameByType.setString(3, "JPEG");
            selectFileNameByType.setString(4, "jpg");
            ResultSet resultSet = selectFileNameByType.executeQuery();
            while (resultSet.next()) {
                String fileName = resultSet.getString("file_name");
                list.add(fileName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(conn);
        }
        return list;
    }

    @Override
    public TextFile selectTextFileByUserIdAndFileName(int userId, String fileName) {

        Connection conn = null;
        TextFile textFile = null;
        try {
            conn = JdbcUtils.getConnection();
            String username = getUserNameById(conn, userId);
            String fileType = getFileTypeByFileNameFromCorrespondingUserTable(conn, fileName, username);
            String tableName = username + fileType;
            PreparedStatement selectTextFile = conn.prepareStatement("select * from " + tableName + " where file_name=?");
            selectTextFile.setString(1,fileName);
            ResultSet resultSet = selectTextFile.executeQuery();
            resultSet.next();
            textFile = new TextFile();
            textFile.setFilename(resultSet.getString("file_name"));
            textFile.setFileType(fileType);
            textFile.setFileContent(resultSet.getString("content"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(conn);
        }
        return textFile;
    }

    @Override
    public void insertByFileNameAndTypeAndDetails(int userId, String fileName, String fileType, InputStream is, String fileContent) {
        Connection conn = JdbcUtils.getConnection();
        try {
            fileType = fileType.substring(1);
            conn.setAutoCommit(false);
            PreparedStatement selectUserNameByUserId = conn.prepareStatement("select username from users where userid=?");
            selectUserNameByUserId.setString(1, String.valueOf(userId));
            ResultSet resultSet = selectUserNameByUserId.executeQuery();
            resultSet.next();
            String username = getUserNameById(conn, userId);
            String tableName=username+fileType;
            PreparedStatement recordFile = conn.prepareStatement("insert into " + username + " values(default ,?,?)");
            recordFile.setString(1, fileName);
            recordFile.setString(2, fileType);
            recordFile.execute();
            if (fileTableNotExist(conn, tableName)) {
                if ("txt".equals(fileType)) {
                    creatTxtTable(conn, tableName);
                } else {
                    creatBlobTable(conn, tableName);
                }
            }
            PreparedStatement insertContentcorrespondingTable = conn.prepareStatement("insert into " + tableName + " values (default,?,?)");
            insertContentcorrespondingTable.setString(1, fileName);
            if (fileContent != null) {
                insertContentcorrespondingTable.setString(2, fileContent);
            } else {
                insertContentcorrespondingTable.setBinaryStream(2, is);
            }
            insertContentcorrespondingTable.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            JdbcUtils.rollback(conn);
        } finally {
            JdbcUtils.closeConnection(conn);
        }

    }

    @Override
    public BinaryFile selectBinaryFileByUserIdAndFileName(int userId, String fileName) {
        Connection conn = null;
        BinaryFile binaryFile = null;
        try {
            conn = JdbcUtils.getConnection();
            String username = getUserNameById(conn, userId);
            String fileType = getFileTypeByFileNameFromCorrespondingUserTable(conn, fileName, username);
            ResultSet resultSet = getCorrespondingBinaryResultSetByByUserIdAndFileName(conn, userId, fileName);
            binaryFile = new BinaryFile();
            binaryFile.setFilename(resultSet.getString("file_name"));
            binaryFile.setFileType(fileType);
            binaryFile.setIs(resultSet.getBinaryStream("content"));
        } catch (SQLException  e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(conn);
        }
        return binaryFile;
    }

    @Override
    public String onlySelectFileType(int userId, String fileName) {
        String fileType = null;
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String username = getUserNameById(conn, userId);
            fileType = getFileTypeByFileNameFromCorrespondingUserTable(conn, fileName, username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(conn);
        }
        return fileType;
    }

    private ResultSet getCorrespondingBinaryResultSetByByUserIdAndFileName(Connection conn, int userId, String fileName) {
        ResultSet resultSet = null;
        try {
            String username = getUserNameById(conn, userId);
            String fileType = getFileTypeByFileNameFromCorrespondingUserTable(conn, fileName, username);
            String tableName = username + fileType;
            PreparedStatement selectTextFile = conn.prepareStatement("select * from " + tableName + " where file_name=?");
            selectTextFile.setString(1,fileName);
            resultSet = selectTextFile.executeQuery();
            resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    private String getFileTypeByFileNameFromCorrespondingUserTable(Connection conn, String fileName, String userName) {
        String fileType = null;
        try {

            PreparedStatement preparedStatement = conn.prepareStatement("select file_type from " + userName + " where  file_name = ?");
            preparedStatement.setString(1, fileName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            fileType = resultSet.getString("file_type");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return fileType;
    }

    private List<String> findDocumentById(int userid) {
        List<String> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String username = getUserNameById(conn, userid);
            PreparedStatement selectFileNameByType = conn.prepareStatement("select file_name from " + username + " where  file_type in (?,?) ");
            selectFileNameByType.setString(1, "txt");
            selectFileNameByType.setString(2, "pdf");
            ResultSet resultSet = selectFileNameByType.executeQuery();
            while (resultSet.next()) {
                String file_name = resultSet.getString("file_name");
                list.add(file_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(conn);
        }
        return list;
    }
}
