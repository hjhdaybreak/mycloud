package com.bee.web.filter;

import com.bee.commons.Constants;
import com.bee.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter(urlPatterns = {"*.do","*.jsp"})
public class UserLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        if (requestURI.contains("login.jsp") || requestURI.contains("login.do") ||requestURI.contains("addUser.jsp") || requestURI.contains("register.do") ||requestURI.contains("ok.jsp") || requestURI.contains("registerOk.jsp")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
            if (user != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                request.setAttribute(Constants.REQUEST_MSG, "你无权访问该页面，请先登录");
                request.getRequestDispatcher("login.jsp").forward(servletRequest, servletResponse);
            }
        }
    }
    @Override
    public void destroy() {

    }
}