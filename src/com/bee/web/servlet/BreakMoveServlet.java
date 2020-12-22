package com.bee.web.servlet;

import com.bee.commons.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

@WebServlet("/testbreak.do")
public class BreakMoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            session.setAttribute("needStop", true);
            String typeFlag = req.getParameter("typeFlag");
            ConcurrentSkipListSet<String> list = (ConcurrentSkipListSet<String>) session.getAttribute(Constants.DOWNLOAD_LIST);
            if ("picture".equals(typeFlag))
                req.getRequestDispatcher("filemanage/pictureStop.jsp").forward(req, resp);
            else
                req.getRequestDispatcher("filemanage/documentStop.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}