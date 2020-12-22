package com.bee.web.servlet;

import com.bee.commons.Constants;
import com.bee.pojo.User;
import com.bee.service.CloudResourceManagementService;
import com.bee.service.impl.CloudResourceManagementServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


@WebServlet(urlPatterns = "/uploadfile.do")
@MultipartConfig
public class UploadFileByPartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");// 设置编码否则 文件名中文乱码
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute(Constants.USER_SESSION_KEY);
        int userId = user.getUserId();
        Part part = req.getPart("file");
        part.getSubmittedFileName();
        String submittedFileName = part.getSubmittedFileName();
        String filename = part.getSubmittedFileName().substring(0, submittedFileName.lastIndexOf("."));
        String fileType = part.getSubmittedFileName().substring(submittedFileName.lastIndexOf("."));
        InputStream is = part.getInputStream();
        if (".txt".equals(fileType)) {
            this.uploadText(req, resp, userId, filename, fileType, is);
        } else if (".pdf".equals(fileType) ||".PDF".equals(fileType) || ".GIF".equals(fileType) || ".JPEG".equals(fileType) || ".jpg".equals(fileType) || ".JPG".equals(fileType)) {
            this.uploadPictureOrPdf(req, resp, userId, filename, fileType, is);
        } else {
            resp.sendRedirect("error.jsp");
        }
    }

    private void uploadText(HttpServletRequest req, HttpServletResponse resp, int userId, String filename, String fileType, InputStream is) throws IOException {
        String str;
        String content = "";
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            while ((str = bfr.readLine()) != null) {
                str += "\n";
                content = content.concat(str);
            }
            CloudResourceManagementService cloudResourceManagementService = new CloudResourceManagementServiceImpl();
            cloudResourceManagementService.uploadFile(userId, filename, fileType, null, content);

            resp.sendRedirect("ok.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }

    private void uploadPictureOrPdf(HttpServletRequest req, HttpServletResponse resp, int userId, String filename, String fileType, InputStream is) throws IOException {
        try {
            CloudResourceManagementService cloudResourceManagementService = new CloudResourceManagementServiceImpl();
            cloudResourceManagementService.uploadFile(userId, filename, fileType, is, null);
            resp.sendRedirect("ok.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }
}