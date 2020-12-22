package com.bee.web.servlet;

import com.bee.commons.Constants;
import com.bee.pojo.User;
import com.bee.service.UserRegisterService;
import com.bee.service.impl.UserRegisterServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register.do")
public class UserRegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.addUser(req, resp);
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = this.createUser(req);
        try {
            UserRegisterService userRegisterService = new UserRegisterServiceImpl();
            userRegisterService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
        resp.sendRedirect("registerOk.jsp");
    }

    private User createUser(HttpServletRequest req) {
        String username = req.getParameter("username");
        String userpwd = req.getParameter("userpwd");
        int vip = Integer.parseInt(req.getParameter("vip"));
        User users = new User();
        users.setUsername(username);
        users.setUserpwd(userpwd);
        users.setMember(vip);
        return users;
    }
}