package com.bee.web.servlet;

import com.bee.commons.Constants;
import com.bee.exception.UserNotFoundException;
import com.bee.pojo.User;
import com.bee.service.UserLoginService;
import com.bee.service.impl.UserLoginServiceImpl;

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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;


@WebServlet("/login.do")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String userpwd = req.getParameter("userpwd");
        try {
            UserLoginService userLoginService = new UserLoginServiceImpl();
            User user = userLoginService.userLogin(username, userpwd);
            HttpSession session = req.getSession(true);
            //建立客户端与服务端的会话状态 并可以通过Filter 判断是否有登录权限
            session.setAttribute(Constants.USER_SESSION_KEY, user);
            //用于防止一个账号同时在线
            HttpSession prev_session = (HttpSession) session.getAttribute(user.getUserId() + "");
            if (prev_session != null) {
                session.removeAttribute(user.getUserId() + "");
                prev_session.invalidate();
            }
            //用来管理文件下载暂停与继续
            //使用并发容器管理文件  如果用ArrayList 下载文件同时结束的时候 从Arraylist中移除文件 可能会出文件都下载完成 但是任然显示还有为下载完全的文件
            //使用这个并发容器 可以防止删除时冲突 他是先标记 然后遍历时候再删除
            ConcurrentSkipListSet<String> skipListSet = new ConcurrentSkipListSet<>();
            session.setAttribute(Constants.DOWNLOAD_LIST, skipListSet);
            //用来支持用同名文件的下载 每次下载文件名的末尾增加个递增的序号
            ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<>();
            session.setAttribute(Constants.SAME_FILE_INCREASE_ORDER, hashMap);
            //用来记录所有下过文件的名族
            List<String> list = new ArrayList<>();
            session.setAttribute(Constants.ALL_HAVE_DOWN_FILE, list);
            //用于防止一个账号同时在线
            session.setAttribute(user.getUserId() + "", session);
            resp.sendRedirect("main.jsp");
        } catch (UserNotFoundException e) {
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect("error.jsp");
        }
    }
}