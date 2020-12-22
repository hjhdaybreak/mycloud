package com.bee.web.servlet;

import com.bee.commons.Constants;
import com.bee.pojo.BinaryFile;
import com.bee.pojo.User;
import com.bee.service.CloudResourceManagementService;
import com.bee.service.impl.CloudResourceManagementServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/seePicture.do")
public class PreViewPictureOrPdfServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
        int userId = user.getUserId();
        String fileName = req.getParameter("fileName");
        CloudResourceManagementService cloudResourceManagementService = new CloudResourceManagementServiceImpl();
        BinaryFile binaryFile = cloudResourceManagementService.findBinaryFileByUserIdAndFileName(userId, fileName);
        String responseType = new DownloadToLocalOrViewServlet().getResponseType(req, resp, false);
        resp.setContentType(responseType);
        InputStream is = binaryFile.getIs();
        int available = is.available();
        byte[] buff = new byte[available];
        is.read(buff);
        OutputStream out = resp.getOutputStream();
        out.write(buff);
        is.close();
        out.close();
    }
}