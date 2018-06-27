package com.books.servlets.filters;

import com.books.servlets.BookCreateServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        this.context = filterConfig.getServletContext();
        LOGGER.info("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        this.context.log("Requested Resource::" + uri);
        if (uri.endsWith("css")) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = req.getSession(true);
        Object user = null;
        user = session.getAttribute("user");
        if (user == null && !(uri.endsWith("loginPage") || uri.endsWith("LoginServlet"))) {
            session.setAttribute("message", "Sign in please");
            this.context.log("Unauthorized access request");
            resp.sendRedirect("/loginPage");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        LOGGER.info("AuthenticationFilter destroyed");
    }

}
