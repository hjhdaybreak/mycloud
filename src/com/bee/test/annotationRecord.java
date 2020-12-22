package com.bee.test;

public class annotationRecord {
    //
//    @Override
//    public void insertDocumentByDocumentNameAndTypeAndContent(int userId, String fileName, String fileType, String fileContent) {
//        Connection conn = null;
//        if (".txt".equals(fileType)) {
//            try {
//                fileType = fileType.substring(1);//
//                conn = JdbcUtils.getConnection();//
//                conn.setAutoCommit(false);//
//                PreparedStatement selectUserNameByUserId = conn.prepareStatement("select username from users where userid=?");//
//                selectUserNameByUserId.setString(1, String.valueOf(userId));//
//                ResultSet resultSet = selectUserNameByUserId.executeQuery();//
//                resultSet.next();//
//                String username = getUserNameById(conn, userId);//
//                PreparedStatement recordFile = conn.prepareStatement("insert into " + username + " values(default ,?,?)");//
//                recordFile.setString(1, fileName);//
//                recordFile.setString(2, fileType);//
//                recordFile.execute();//
//                String tableName = username + fileType;//
//                if (FileTableNotExist(conn, tableName)) {//
//                    creatTxtTable(conn, tableName);//
//                }//
//                PreparedStatement insertContentcorrespondingTable = conn.prepareStatement("insert into " + tableName + " values (default,?,?)");
//                insertContentcorrespondingTable.setString(1, fileName);
//                insertContentcorrespondingTable.setString(2, fileContent);
//                insertContentcorrespondingTable.execute();
//                conn.commit();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//                JdbcUtils.rollback(conn);
//            } finally {
//                JdbcUtils.closeConnection(conn);
//            }
//        }
//    }


//    @Override
//    public void insertPictureByPictureNameAndTypeAndContent(int userid, String fileName, String fileType, InputStream in) {
//        Connection conn = null;
//        if (".png".equals(fileType) || ".GIF".equals(fileType) || ".JPEG".equals(fileType) || ".jpg".equals(fileType)) {
//            try {
//                fileType = fileType.substring(1);//
//                conn = JdbcUtils.getConnection();//
//                conn.setAutoCommit(false);//
//                PreparedStatement selectUserNameByUserId = conn.prepareStatement("select username from users where userid=?");//
//                selectUserNameByUserId.setString(1, String.valueOf(userid));//
//                ResultSet resultSet = selectUserNameByUserId.executeQuery();//
//                resultSet.next();//
//                String username = getUserNameById(conn, userid);//
//                PreparedStatement recordFile = conn.prepareStatement("insert into " + username + " values(default ,?,?)");//
//                recordFile.setString(1, fileName);//
//                recordFile.setString(2, fileType);//
//                recordFile.execute();//
//                String tableName = username + fileType;//
//                if (FileTableNotExist(conn, tableName)) {//
//                    creatBlobTable(conn, tableName);//
//                }//
//                PreparedStatement insertContentcorrespondingTable = conn.prepareStatement("insert into " + tableName + " values (default,?,?)");//
//                insertContentcorrespondingTable.setString(1, fileName);
//                insertContentcorrespondingTable.setBinaryStream(2, in);
//                insertContentcorrespondingTable.execute();
//                conn.commit();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                JdbcUtils.rollback(conn);
//            } finally {
//                JdbcUtils.closeConnection(conn);
//            }
//        }
//    }



    //            String username = getUserNameById(conn, userId);
//            String fileType = getFileTypeByFileNameFromUserTable(conn, fileName, username);
//            String tableName = username + fileType;
//            PreparedStatement selectTextFile = conn.prepareStatement("select * from " + tableName + "");
//            ResultSet resultSet = selectTextFile.executeQuery();
//            resultSet.next();//

    //    void insertDocumentByDocumentNameAndTypeAndContent(int userid, String fileName, String fileType, String fileContent);
//
//    void insertPictureByPictureNameAndTypeAndContent(int userid, String fileName, String fileType, InputStream in);


    //
//    @Override
//    public void uploadDocument(int userId, String fileName, String fileType, String content) {
//        CloudResourceManageDao cloudResourceManageDao = new CloudResourceManageDaoImpl();
////        cloudResourceManageDao.insertDocumentByDocumentNameAndTypeAndContent(userid, fileName, type, content);
//        cloudResourceManageDao.insertByFileNameAndTypeAndDetails(userId, fileName, fileType, null, content);
//    }

//    @Override
//    public void uploadPicture(int userId, String fileName, String filetype, InputStream in) {
//        CloudResourceManageDao cloudResourceManageDao = new CloudResourceManageDaoImpl();
//        cloudResourceManageDao.insertByFileNameAndTypeAndDetails(userId, fileName, filetype, in, null);
////        cloudResourceManageDao.insertPictureByPictureNameAndTypeAndContent(userid, filename, filetype, in);
//    }

    //    void uploadDocument(int userid, String filename, String filetype, String content);
//
//    void uploadPicture(int userid, String filename, String filetype, InputStream in);

    //        if ("text/plain".equals(responseType)) {
//            resp.setContentType("text/plain");
//        } else if ("image/gif".equals(responseType)) {
//            resp.setContentType("image/gif");
//        } else if ("image/png".equals(responseType)) {
//            resp.setContentType("image/png");
//        } else if ("image/jpeg".equals(responseType)) {
//            resp.setContentType("image/jpeg");
//        } else {
//            resp.sendRedirect("error.jsp");
//        }


    //            byte[] buff = new byte[1024];
//            int len;
//            long startTime = System.currentTimeMillis();
//            while ((len = in.read(buff)) != -1) {
//                current = current + len;
//                os.write(buff);
//                allow = (Boolean) servletContext.getAttribute("allow");
//                while (allow) {
//                    allow = (Boolean) servletContext.getAttribute("allow");
//                }
//                if (current > speed) {
//                    startPause(startTime + 1000);
//                    current = 0;
//                    startTime = System.currentTimeMillis();
//                }
//            }
//            os.close();
//            fos.close();


    //            Boolean allow;
//            byte[] buff = new byte[1024];
//            int len;
//            long startTime = System.currentTimeMillis();
//            while ((len = is.read(buff)) != -1) {
//                current = current + len;
//                os.write(buff);
//                allow = (Boolean) servletContext.getAttribute("allow");
//                while (allow) {
//                    allow = (Boolean) servletContext.getAttribute("allow");
//                }
//                if (current > speed) {
//                    startPause(startTime + 1000);
//                    current = 0;
//                    startTime = System.currentTimeMillis();
//                }
//            }
//            os.close();
}