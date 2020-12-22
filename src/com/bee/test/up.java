package com.bee.test;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UP
 */
@MultipartConfig
@WebServlet("/upload.do")
public class up extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 从part对象里，解析出文件名
     * @param part
     * @return
     */
    private String getFilename(Part part) {
        String header   = part.getHeader("content-disposition");
        String filename = header.substring(header.indexOf("filename=\"")+10,header.lastIndexOf("\""));
        return filename;
    }

    public up() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");//获取上传的文件对象
        saveToDb(part);//调用方法保存文件到数据库
    }

    private void saveToDb(Part part) {

        InputStream in =null;//初始化一个输入流对象
        String filename = getFilename(part);//调用方法获取文件名

        try {
            in = part.getInputStream();//获取文件输入流
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        String url ="jdbc:mysql://149.129.112.161:3306/shop?useUnicode=true&characterEncoding=utf-8";
        String user="shop";
        String password="999999";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            String sql = "insert into bigtb (name,images) values(?,?) ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, filename);//设置文件名
            statement.setBinaryStream(2, in);//设置输入流

            statement.executeUpdate();//处理sql语句

            statement.close();
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

    }


}