package com.bee.web.servlet;

import com.bee.commons.Constants;
import com.bee.pojo.User;
import com.bee.service.CloudResourceManagementService;
import com.bee.service.impl.CloudResourceManagementServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewFile.do")
public class ResourceShowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String flag = req.getParameter("flag");
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute(Constants.USER_SESSION_KEY);
        int userId = user.getUserId();
        if ("document".equals(flag)) {
            this.handleDocument(req, resp, userId);
        } else
            this.handlePicture(req, resp, userId);
    }
    private void handleDocument(HttpServletRequest req, HttpServletResponse resp, int userId) throws IOException {
        try {
            List<String> documentList = showDocument(userId);
            req.setAttribute("documentList", documentList);
            req.getRequestDispatcher("filemanage/document.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }

    private void handlePicture(HttpServletRequest req, HttpServletResponse resp, int userId) throws IOException {
        try {
            List<String> picList = showPicture(userId);
            req.setAttribute("pictureList", picList);
            req.getRequestDispatcher("filemanage/picture.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }

    private List<String> showDocument(int userId) {
        CloudResourceManagementService cloudResourceManagementService = new CloudResourceManagementServiceImpl();
        return cloudResourceManagementService.showFileByUserIdAndType(userId, "document");
    }


    private List<String> showPicture(int userId) {
        CloudResourceManagementService cloudResourceManagementService = new CloudResourceManagementServiceImpl();
        return cloudResourceManagementService.showFileByUserIdAndType(userId, "picture");
    }
}